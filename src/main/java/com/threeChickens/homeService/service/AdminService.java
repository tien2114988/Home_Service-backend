package com.threeChickens.homeService.service;

import com.threeChickens.homeService.dto.auth.AdminLoginDto;
import com.threeChickens.homeService.dto.auth.JwtDto;
import com.threeChickens.homeService.entity.Admin;
import com.threeChickens.homeService.exception.AppException;
import com.threeChickens.homeService.exception.StatusCode;
import com.threeChickens.homeService.repository.AdminRepository;
import com.threeChickens.homeService.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${admin.username}")
    private String username;

    @Value("${admin.password}")
    private String password;

    public void initAdminAccounts(){
        if (adminRepository.count()<=0) {
            String encodedPassword = passwordEncoder.encode(password);
            Admin admin = Admin.builder().userName(username).password(encodedPassword).build();
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
}
