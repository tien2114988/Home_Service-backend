package com.threeChickens.homeService.service;

import com.nimbusds.jose.JOSEException;
import com.threeChickens.homeService.dto.address.GetAddressDto;
import com.threeChickens.homeService.dto.auth.LoginDto;
import com.threeChickens.homeService.dto.auth.OtpDto;
import com.threeChickens.homeService.dto.auth.SignUpDto;
import com.threeChickens.homeService.dto.address.CreateAddressDto;
import com.threeChickens.homeService.dto.googleAuth.GoogleSignupDto;
import com.threeChickens.homeService.dto.googleAuth.UserInfoDto;
import com.threeChickens.homeService.dto.user.GetUserDto;
import com.threeChickens.homeService.dto.user.UpdateUserDto;
import com.threeChickens.homeService.entity.Address;
import com.threeChickens.homeService.entity.User;
import com.threeChickens.homeService.entity.Ward;
import com.threeChickens.homeService.enums.AccountType;
import com.threeChickens.homeService.enums.Gender;
import com.threeChickens.homeService.enums.UserRole;
import com.threeChickens.homeService.enums.UserStatus;
import com.threeChickens.homeService.exception.AppException;
import com.threeChickens.homeService.exception.StatusCode;
import com.threeChickens.homeService.repository.UserRepository;
import com.threeChickens.homeService.utils.JwtUtil;
import com.threeChickens.homeService.utils.VietQrUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public void initUsers(){
        if(userRepository.count()==0){
            String encodedPassword = passwordEncoder.encode("123456");
            User customer1 = User.builder()
                    .name("Nguyễn Đại Tiến")
                    .email("tien.nguyen2283@hcmut.edu.vn")
                    .role(UserRole.CUSTOMER)
                    .accountType(AccountType.EMAIL)
                    .dob(new Date())
                    .balance(1000000)
                    .gender(Gender.MALE)
                    .phoneNumber("0346066323")
                    .reputationPoint(100)
                    .status(UserStatus.ACTIVE)
                    .password(encodedPassword)
                    .build();
            User customer2 = User.builder()
                    .name("Nguyễn Trương Phước Thọ")
                    .email("phuoctho150420@gmail.com")
                    .role(UserRole.CUSTOMER)
                    .accountType(AccountType.EMAIL)
                    .dob(new Date())
                    .balance(6000000)
                    .gender(Gender.MALE)
                    .phoneNumber("0123456789")
                    .reputationPoint(100)
                    .status(UserStatus.ACTIVE)
                    .password(encodedPassword)
                    .build();
            User customer3 = User.builder()
                    .name("Bùi Tiến Dũng")
                    .email("dung.buitiendung03@hcmut.edu.vn")
                    .role(UserRole.CUSTOMER)
                    .accountType(AccountType.EMAIL)
                    .dob(new Date())
                    .balance(5000000)
                    .gender(Gender.MALE)
                    .phoneNumber("0123456789")
                    .reputationPoint(100)
                    .status(UserStatus.ACTIVE)
                    .password(encodedPassword)
                    .build();
            User freelancer1 = User.builder()
                    .name("Tiến Nguyễn Đại")
                    .email("tien.nguyen2283@hcmut.edu.vn")
                    .role(UserRole.FREELANCER)
                    .accountType(AccountType.GOOGLE)
                    .dob(new Date())
                    .balance(1000000)
                    .gender(Gender.MALE)
                    .phoneNumber("0346066323")
                    .reputationPoint(100)
                    .status(UserStatus.ACTIVE)
                    .build();
            User freelancer2 = User.builder()
                    .name("Trương Phước Thọ Nguyễn")
                    .email("phuoctho150420@gmail.com")
                    .role(UserRole.FREELANCER)
                    .accountType(AccountType.GOOGLE)
                    .dob(new Date())
                    .balance(6000000)
                    .gender(Gender.MALE)
                    .phoneNumber("0123456789")
                    .reputationPoint(100)
                    .status(UserStatus.ACTIVE)
                    .password(encodedPassword)
                    .build();
            User freelancer3 = User.builder()
                    .name("Tiến Dũng Bùi")
                    .email("dung.buitiendung03@hcmut.edu.vn")
                    .role(UserRole.FREELANCER)
                    .accountType(AccountType.GOOGLE)
                    .dob(new Date())
                    .balance(5000000)
                    .gender(Gender.MALE)
                    .phoneNumber("0123456789")
                    .reputationPoint(100)
                    .status(UserStatus.ACTIVE)
                    .build();

            userRepository.save(customer1);
            userRepository.save(customer2);
            userRepository.save(customer3);
            userRepository.save(freelancer1);
            userRepository.save(freelancer2);
            userRepository.save(freelancer3);
        }
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

    public GetUserDto getUserByEmailAndPassword(LoginDto loginDto){
        User user = userRepository.findByEmailAndAccountTypeAndDeletedIsFalse(loginDto.getEmail(), AccountType.EMAIL).orElseThrow(() -> new AppException(StatusCode.EMAIL_NOT_FOUND));

        boolean isValid = passwordEncoder.matches(loginDto.getPassword(), user.getPassword());

        if(!isValid){
            throw new AppException(StatusCode.PASSWORD_INVALID);
        }

        String jwt = jwtUtil.generateToken(user.getId(), user.getRole().toString());
        GetUserDto getUserDto = modelMapper.map(user, GetUserDto.class);
        getUserDto.setJwt(jwt);

        return getUserDto;
    }

    public boolean existUserByEmail(String email, AccountType accountType, boolean isReturn) {
        boolean isExist = userRepository.existsByEmailAndDeletedIsFalseAndAccountType(email, accountType);
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
            if(!userRepository.existsByEmailAndRoleAndAccountTypeAndDeletedIsFalse(otpDto.getEmail(), role, AccountType.EMAIL)){
                throw new AppException(StatusCode.EMAIL_NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            throw new AppException(StatusCode.ROLE_INVALID);
        }
    }

    public GetUserDto getUserByJwt(String jwt) throws ParseException, JOSEException {
        jwtUtil.verifyToken(jwt);

        String userId = jwtUtil.getUserIdFromJWT(jwt);
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(StatusCode.USER_NOT_FOUND));

        GetUserDto getUserDto = modelMapper.map(user, GetUserDto.class);
        getUserDto.setJwt(jwt);

        return getUserDto;
    }

    public GetUserDto createUser(SignUpDto signUpDto) {
        User user = modelMapper.map(signUpDto,User.class);

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        try {
            user.setRole(UserRole.valueOf(signUpDto.getRole()));
        } catch (IllegalArgumentException e) {
            throw new AppException(StatusCode.ROLE_INVALID);
        }

        user.setReputationPoint(100);
        user.setAccountType(AccountType.EMAIL);

        user = userRepository.save(user);

        GetUserDto getUserDto = modelMapper.map(user,GetUserDto.class);
        String jwt = jwtUtil.generateToken(user.getId(), user.getRole().toString());
        getUserDto.setJwt(jwt);
        return getUserDto;
    }

    public GetUserDto getUserByGoogle(UserInfoDto userInfoDto){
        User user = userRepository.findByEmailAndAccountTypeAndDeletedIsFalse(userInfoDto.getEmail(), AccountType.GOOGLE).orElse(
                User.builder().email(userInfoDto.getEmail())
                        .name(userInfoDto.getName())
                        .avatar(userInfoDto.getPicture())
                        .googleSub(userInfoDto.getSub())
                        .build()
        );


        GetUserDto getUserDto = modelMapper.map(user, GetUserDto.class);
        if(user.getId()!=null){
            String jwt = jwtUtil.generateToken(user.getId(), user.getRole().toString());
            getUserDto.setJwt(jwt);
        }
        return getUserDto;
    }

    public GetUserDto createUserByGoogle(GoogleSignupDto googleSignupDto){
        User user = modelMapper.map(googleSignupDto,User.class);

        user.setReputationPoint(100);
        user.setAccountType(AccountType.GOOGLE);

        user = userRepository.save(user);

        GetUserDto getUserDto = modelMapper.map(user,GetUserDto.class);
        String jwt = jwtUtil.generateToken(user.getId(), user.getRole().toString());
        getUserDto.setJwt(jwt);
        return getUserDto;
    }

    //user_info
    public List<GetUserDto> getUsers(){
        List<User> users = userRepository.findAllByDeletedIsFalse();
        return users.stream().map(user-> {
                    GetUserDto getUserDto = modelMapper.map(user, GetUserDto.class);
                    getUserDto.setAddresses(
                            getUserDto.getAddresses().stream().filter(
                                    address -> !address.isDeleted()
                            ).collect(Collectors.toSet())
                    );
                    return getUserDto;
                }
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
}
