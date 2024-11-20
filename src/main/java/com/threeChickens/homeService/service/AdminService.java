package com.threeChickens.homeService.service;

import com.nimbusds.jose.JOSEException;
import com.threeChickens.homeService.dto.admin.AdminDto;
import com.threeChickens.homeService.dto.auth.AdminLoginDto;
import com.threeChickens.homeService.dto.auth.JwtDto;
import com.threeChickens.homeService.dto.user.GetUserDto;
import com.threeChickens.homeService.entity.Admin;
import com.threeChickens.homeService.entity.User;
import com.threeChickens.homeService.exception.AppException;
import com.threeChickens.homeService.exception.StatusCode;
import com.threeChickens.homeService.repository.AdminRepository;
import com.threeChickens.homeService.utils.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${admin.username}")
    private String username;

    @Value("${admin.password}")
    private String password;

    public void initAdminAccounts(){
        if (adminRepository.count()<=0) {
            String encodedPassword = passwordEncoder.encode(password);
            Admin admin = Admin.builder().name("Admin").userName(username).password(encodedPassword).build();
            adminRepository.save(admin);
        }
    }

    public JwtDto checkAdmin(AdminLoginDto adminLoginDto){
        Admin admin = adminRepository.findByUserName(adminLoginDto.getUsername()).orElseThrow(
                ()->new AppException(StatusCode.ADMIN_NOT_FOUND)
        );

        boolean isValid = passwordEncoder.matches(adminLoginDto.getPassword(), admin.getPassword());

        if(!isValid){
            throw new AppException(StatusCode.PASSWORD_INVALID);
        }

        String jwt = jwtUtil.generateToken(admin.getId(), "ADMIN");

        return JwtDto.builder().jwt(jwt).build();
    }

    public AdminDto getAdminByJwt(String jwt) throws ParseException, JOSEException {
        jwtUtil.verifyToken(jwt);

        String adminId = jwtUtil.getUserIdFromJWT(jwt);
        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new AppException(StatusCode.ADMIN_NOT_FOUND));

        AdminDto adminDto = modelMapper.map(admin, AdminDto.class);
        adminDto.setJwt(jwt);

        return adminDto;
    }
}
