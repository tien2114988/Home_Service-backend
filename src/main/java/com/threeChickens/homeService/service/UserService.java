package com.threeChickens.homeService.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import com.threeChickens.homeService.dto.auth.LoginDto;
import com.threeChickens.homeService.dto.auth.OtpDto;
import com.threeChickens.homeService.dto.auth.SignUpDto;
import com.threeChickens.homeService.dto.user.UserDto;
import com.threeChickens.homeService.entity.Bank;
import com.threeChickens.homeService.entity.BankAccount;
import com.threeChickens.homeService.entity.User;
import com.threeChickens.homeService.enums.UserRole;
import com.threeChickens.homeService.exception.AppException;
import com.threeChickens.homeService.exception.StatusCode;
import com.threeChickens.homeService.repository.BankAccountRepository;
import com.threeChickens.homeService.repository.BankRepository;
import com.threeChickens.homeService.repository.UserRepository;
import com.threeChickens.homeService.utils.JwtUtil;
import com.threeChickens.homeService.utils.VietQrUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankService bankService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private VietQrUtil vietQrUtil;

    public UserDto getUserByEmailAndPassword(LoginDto loginDto){
        User user = userRepository.findByEmailAndDeletedIsFalse(loginDto.getEmail()).orElseThrow(() -> new AppException(StatusCode.EMAIL_NOT_FOUND));

        boolean isValid = passwordEncoder.matches(loginDto.getPassword(), user.getPassword());

        if(!isValid){
            throw new AppException(StatusCode.PASSWORD_INVALID);
        }

        String jwt = jwtUtil.generateToken(user.getId(), user.getRole().toString());
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setJwt(jwt);

        return userDto;
    }

    public boolean existUserByEmail(String email, boolean isReturn) {
        if(isReturn){
            return userRepository.existsByEmailAndDeletedIsFalse(email);
        }
        if(userRepository.existsByEmailAndDeletedIsFalse(email)){
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

    public UserDto getUserByJwt(String jwt) throws ParseException, JOSEException {
        jwtUtil.verifyToken(jwt);

        String userId = jwtUtil.getUserIdFromJWT(jwt);
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(StatusCode.USER_NOT_FOUND));

        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setJwt(jwt);

        return userDto;
    }


    public void createUser(SignUpDto signUpDto) {
        User user = modelMapper.map(signUpDto,User.class);

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        try {
            user.setRole(UserRole.valueOf(signUpDto.getRole()));
        } catch (IllegalArgumentException e) {
            throw new AppException(StatusCode.ROLE_INVALID);
        }

        user.setReputationPoint(100);

        user = userRepository.save(user);

//        GetUserDto getUserDto = modelMapper.map(user,GetUserDto.class);
//        getUserDto.setRole(user.getRole().getId());
//        return getUserDto;
    }

    //user_info
    public List<UserDto> getAllUsers(){
        List<User> users = userRepository.findAllByDeletedIsFalse();
        return users.stream().map(user->modelMapper.map(user, UserDto.class)).toList();
    }

    public UserDto updateUser(String id, UserDto userDto) throws Exception {
        User user = userRepository.findByIdAndDeletedIsFalse(id).orElseThrow(() -> new AppException(StatusCode.USER_NOT_FOUND));
        modelMapper.map(userDto, user);

        if(userDto.getBankAccount()!=null){
            bankService.updateBankAccountForUser(user, userDto.getBankAccount());
        }

        user = userRepository.save(user);

        return modelMapper.map(user, UserDto.class);
    }

}
