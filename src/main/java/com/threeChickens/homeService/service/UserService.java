package com.threeChickens.homeService.service;

import com.nimbusds.jose.JOSEException;
import com.threeChickens.homeService.dto.address.GetAddressDto;
import com.threeChickens.homeService.dto.auth.LoginDto;
import com.threeChickens.homeService.dto.auth.OtpDto;
import com.threeChickens.homeService.dto.auth.SignUpDto;
import com.threeChickens.homeService.dto.address.CreateAddressDto;
import com.threeChickens.homeService.dto.googleAuth.GoogleSignupDto;
import com.threeChickens.homeService.dto.googleAuth.UserInfoDto;
import com.threeChickens.homeService.dto.notification.GetNotificationDto;
import com.threeChickens.homeService.dto.notification.RedisNotificationDto;
import com.threeChickens.homeService.dto.payment.PayOsDto;
import com.threeChickens.homeService.dto.payment.PayOsWebhookDataDto;
import com.threeChickens.homeService.dto.user.GetUserDetailDto;
import com.threeChickens.homeService.dto.user.GetUserDto;
import com.threeChickens.homeService.dto.user.PaymentHistoryDto;
import com.threeChickens.homeService.dto.user.UpdateUserDto;
import com.threeChickens.homeService.dto.work.GetFreelancerWorkDto;
import com.threeChickens.homeService.entity.*;
import com.threeChickens.homeService.enums.*;
import com.threeChickens.homeService.exception.AppException;
import com.threeChickens.homeService.exception.StatusCode;
import com.threeChickens.homeService.repository.*;
import com.threeChickens.homeService.utils.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.payos.type.CheckoutResponseData;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankService bankService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PayOsUtil payOsUtil;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ListRedisUtil<RedisNotificationDto> listRedisUtil;

    @Value("${payment.minAmount}")
    private int minAmount;
    @Autowired
    private UserNotificationRepository userNotificationRepository;
    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;

    public String updateAvatar(String id, MultipartFile avatar) {
        User user = getByIdAndRole(id, null);
        String filePath = fileUploadUtil.saveImage(avatar, id);
        user.setAvatar(filePath);
        user = userRepository.save(user);
        return user.getAvatar();
    }

    public User getByIdAndRole(String id, UserRole role){
        if(role==null){
            return userRepository.findByIdAndDeletedIsFalse(id).orElseThrow(
                    () -> new AppException(StatusCode.USER_NOT_FOUND)
            );
        }
        return userRepository.findByIdAndRoleAndDeletedIsFalse(id, role).orElseThrow(
                () -> new AppException(StatusCode.USER_NOT_FOUND)
        );
    }

    public GetUserDetailDto getUserByIdAndRole(String id, String role){
        try {
            User user = getByIdAndRole(id, role != null ? UserRole.valueOf(role) : null);
            return modelMapper.map(user, GetUserDetailDto.class);
        }catch (IllegalArgumentException e) {
            throw new AppException(StatusCode.ROLE_INVALID);
        }
    }

    public GetUserDetailDto getUserByEmail(LoginDto loginDto){
        User user = userRepository.findByEmailAndDeletedIsFalse(loginDto.getEmail()).orElseThrow(() -> new AppException(StatusCode.EMAIL_NOT_FOUND));
        user.setFirebaseToken(loginDto.getFirebaseToken());
        user = userRepository.save(user);
//        boolean isValid = passwordEncoder.matches(loginDto.getPassword(), user.getPassword());

//        if(!isValid){
//            throw new AppException(StatusCode.PASSWORD_INVALID);
//        }

        String jwt = jwtUtil.generateToken(user.getId(), user.getRole().toString());
        GetUserDetailDto getUserDto = modelMapper.map(user, GetUserDetailDto.class);
        getUserDto.setJwt(jwt);

        return getUserDto;
    }

    public boolean existUserByEmail(String email, boolean isReturn) {
        boolean isExist = userRepository.existsByEmailAndDeletedIsFalse(email);
        if(isReturn){
            return isExist;
        }
        if(isExist){
            throw new AppException(StatusCode.EMAIL_EXISTED);
        }
        return true;
    }

    public void existUserByEmailAndRole(OtpDto otpDto) {
        try {
            UserRole role = UserRole.valueOf(otpDto.getRole());
            if(!userRepository.existsByEmailAndRoleAndDeletedIsFalse(otpDto.getEmail(), role)){
                throw new AppException(StatusCode.EMAIL_NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            throw new AppException(StatusCode.ROLE_INVALID);
        }
    }

    public GetUserDetailDto getUserByJwt(String jwt) throws ParseException, JOSEException {
        jwtUtil.verifyToken(jwt);

        String userId = jwtUtil.getUserIdFromJWT(jwt);
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(StatusCode.USER_NOT_FOUND));

        GetUserDetailDto getUserDto = modelMapper.map(user, GetUserDetailDto.class);
        getUserDto.setJwt(jwt);

        return getUserDto;
    }

    public GetUserDetailDto createUser(SignUpDto signUpDto) {
        User user = modelMapper.map(signUpDto,User.class);

//        String encodedPassword = passwordEncoder.encode(user.getPassword());
//        user.setPassword(encodedPassword);

        try {
            user.setRole(UserRole.valueOf(signUpDto.getRole()));
        } catch (IllegalArgumentException e) {
            throw new AppException(StatusCode.ROLE_INVALID);
        }

        user.setReputationPoint(100);
        user.setAccountType(AccountType.EMAIL);
        user.setStatus(UserStatus.ACTIVE);

        user = userRepository.save(user);

        GetUserDetailDto getUserDto = modelMapper.map(user,GetUserDetailDto.class);
        String jwt = jwtUtil.generateToken(user.getId(), user.getRole().toString());
        getUserDto.setJwt(jwt);
        return getUserDto;
    }

    public GetUserDetailDto getUserByGoogle(UserInfoDto userInfoDto){
        User user = userRepository.findByEmailAndDeletedIsFalse(userInfoDto.getEmail()).orElse(
                User.builder().email(userInfoDto.getEmail())
                        .name(userInfoDto.getName())
                        .avatar(userInfoDto.getPicture())
                        .googleSub(userInfoDto.getSub())
                        .build()
        );


        GetUserDetailDto getUserDto = modelMapper.map(user, GetUserDetailDto.class);
        if(user.getId()!=null){
            String jwt = jwtUtil.generateToken(user.getId(), user.getRole().toString());
            getUserDto.setJwt(jwt);
        }
        return getUserDto;
    }

    public GetUserDetailDto createUserByGoogle(GoogleSignupDto googleSignupDto){
        User user = modelMapper.map(googleSignupDto,User.class);

        user.setReputationPoint(100);
        user.setAccountType(AccountType.GOOGLE);

        user = userRepository.save(user);

        GetUserDetailDto getUserDto = modelMapper.map(user,GetUserDetailDto.class);
        String jwt = jwtUtil.generateToken(user.getId(), user.getRole().toString());
        getUserDto.setJwt(jwt);
        return getUserDto;
    }

    //user_info
    public List<GetUserDto> getUsers(int page, int size, String sortBy, String sortDirection, String role, String postId){
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        List<User> users;

        if(role!=null &&  postId !=null){
            Post post = postRepository.findByIdAndDeletedIsFalse(postId).orElseThrow(() -> new AppException(StatusCode.POST_NOT_FOUND));
            try {
                users = userRepository.findFreelancersByPostId(post.getWork().getId(), FreelancerWorkStatus.WORK,UserStatus.PROHIBITIVE, UserRole.valueOf(role), pageable).getContent();
            }catch (IllegalArgumentException e) {
                throw new AppException(StatusCode.ROLE_INVALID);
            }
        }else{
            users = userRepository.findAllByDeletedIsFalse(pageable).getContent();
        }

        return users.stream().map(
                user-> modelMapper.map(user, GetUserDto.class)
        ).toList();
    }


    public GetUserDto updateUser(String id, UpdateUserDto updateUserDto) throws Exception {
        User user = userRepository.findByIdAndDeletedIsFalse(id).orElseThrow(() -> new AppException(StatusCode.USER_NOT_FOUND));
        modelMapper.map(updateUserDto, user);

        if(updateUserDto.getBankAccount()!=null){
            bankService.updateBankAccountForUser(user, updateUserDto.getBankAccount());
        }

        user = userRepository.save(user);

        return modelMapper.map(user, GetUserDto.class);
    }

    public GetAddressDto addAddress(String id, CreateAddressDto createAddressDto){
        User user = getByIdAndRole(id, null);

        return addressService.create(createAddressDto, user);
    }

    public void deleteAddress(String addressId){
        addressService.deletedById(addressId);
    }

    public GetAddressDto updateAddress(String addressId, CreateAddressDto updateAddressDto){
        return addressService.update(addressId, updateAddressDto);
    }

    public String recharge(String id, PayOsDto payOsDto) throws Exception {
        User user = getByIdAndRole(id, null);

        if(payOsDto.getAmount() < minAmount){
            throw new RuntimeException("Nạp tối thiểu "+ NumberFormat.getInstance(Locale.US).format(minAmount)  + " VND");
        }

        CheckoutResponseData checkoutResponseData = payOsUtil.createPaymentLink(payOsDto);
        Payment payment = Payment.builder().amount(payOsDto.getAmount()).orderCode(checkoutResponseData.getOrderCode()).user(user).build();

        paymentRepository.save(payment);
        return checkoutResponseData.getCheckoutUrl();
    }

    @Transactional
    public GetUserDetailDto withdraw(String id, PayOsDto payOsDto){
        User user = getByIdAndRole(id, null);
        if(user.getBalance() < payOsDto.getAmount()){
            throw new AppException(StatusCode.BALANCE_NOT_ENOUGH);
        }
        user.setBalance(user.getBalance()-payOsDto.getAmount());
        user = userRepository.save(user);
        PaymentHistory paymentHistory = PaymentHistory.builder().amount(-payOsDto.getAmount()).refId(UUID.randomUUID().toString()).user(user).build();
        paymentHistoryRepository.save(paymentHistory);
        return modelMapper.map(user, GetUserDetailDto.class);
    }

    public void handleRecharge(PayOsWebhookDataDto data) throws Exception {
        System.out.println("PayOS webhook");
        Payment payment = paymentRepository.findByOrderCode(data.getOrderCode()).orElseThrow(
                ()-> new AppException(StatusCode.ORDER_CODE_NOT_FOUND)
        );
        String status = payOsUtil.getPaymentLinkData(payment.getOrderCode());
        if(status.equals("PAID")){
            System.out.println("PAID");
            User user = payment.getUser();
            user.setBalance(user.getBalance() + payment.getAmount());
            userRepository.save(user);
            PaymentHistory paymentHistory = PaymentHistory.builder().amount(payment.getAmount()).refId(data.getReference()).user(user).build();
            paymentHistoryRepository.save(paymentHistory);
        }
    }

    public List<RedisNotificationDto> getNotificationsByUserId(String id){
        User user = getByIdAndRole(id, null);
        return listRedisUtil.getList(user.getId());
    }

    public List<PaymentHistoryDto> getPaymentHistoriesByUserId(String id){
        User user = getByIdAndRole(id, null);
        return user.getPaymentHistories().stream()
                .sorted(Comparator.comparing(PaymentHistory::getCreatedAt).reversed())
                .map(paymentHistory -> modelMapper.map(paymentHistory ,PaymentHistoryDto.class)).toList();
    }

    public void viewNotification(String userId, int id){
        User user = getByIdAndRole(userId, null);
        RedisNotificationDto redisNotificationDto = listRedisUtil.getValueById(user.getId(), id);
        redisNotificationDto.setView(true);
        listRedisUtil.updateListById(user.getId(), id, redisNotificationDto);
    }

    public List<GetFreelancerWorkDto> getWorksByFreelancerId(String id){
        User freelancer = getByIdAndRole(id, UserRole.FREELANCER);
        return freelancer.getFreelancerWorkServices().stream().map(
                freelancerWorkService -> modelMapper.map(freelancerWorkService, GetFreelancerWorkDto.class)
        ).toList();
    }
}
