package com.threeChickens.homeService.config;

import com.threeChickens.homeService.exception.AuthExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig implements WebMvcConfigurer {
    @Autowired
    private JwtDecoderConfig jwtDecoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(req -> req
                .requestMatchers("**").permitAll()
                // Public
                .requestMatchers(HttpMethod.GET,"/").permitAll()
                .requestMatchers("/data").permitAll()
                .requestMatchers("/api/test/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/larkEvent").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/payOs").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/bankHub").permitAll()
                // Common
//                .requestMatchers(HttpMethod.GET,"/api/attendance/**", "/api/staff/**", "/api/larkAccount/getLink").hasAnyAuthority("SCOPE_ADMIN", "SCOPE_STAFF")
//                .requestMatchers("/api/bank/**").hasAnyAuthority("SCOPE_ADMIN", "SCOPE_STAFF")
//                .requestMatchers("/api/notification/**").hasAnyAuthority("SCOPE_ADMIN", "SCOPE_STAFF")
                // Admin
//                .requestMatchers("/api/larkAccount/**").hasAuthority("SCOPE_ADMIN")
//                .requestMatchers(HttpMethod.GET,"/api/salaryAdvanceReq/company/**").hasAuthority("SCOPE_ADMIN")
//                .requestMatchers("/api/bankAccount/**").hasAuthority("SCOPE_ADMIN")
//                .requestMatchers(HttpMethod.PUT,"/api/companies/**").hasAuthority("SCOPE_ADMIN")
//                .requestMatchers(HttpMethod.POST,"/api/companies/**").hasAuthority("SCOPE_ADMIN")
//                .requestMatchers(HttpMethod.GET,"/api/companies/admin").hasAuthority("SCOPE_ADMIN")
//                .requestMatchers(HttpMethod.PUT,"/api/staff/updateSalary").hasAuthority("SCOPE_ADMIN")
                // Staff
//                .requestMatchers(HttpMethod.POST,"/api/salaryAdvanceReq/**").hasAuthority("SCOPE_STAFF")
//                .requestMatchers(HttpMethod.GET,"/api/salaryAdvanceReq/feePayment/**").hasAuthority("SCOPE_STAFF")
//                .requestMatchers(HttpMethod.GET,"/api/salaryAdvanceReq/staff/**").hasAuthority("SCOPE_STAFF")
//                .requestMatchers(HttpMethod.GET,"/api/companies").hasAuthority("SCOPE_STAFF")
//                .requestMatchers(HttpMethod.POST,"/api/staff/**").hasAuthority("SCOPE_STAFF")
//                .requestMatchers(HttpMethod.PUT,"/api/staff/updateBank").hasAuthority("SCOPE_STAFF")
                // Swagger
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll());

        httpSecurity.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder))
                .authenticationEntryPoint(new AuthExceptionHandler()));

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(source);
    }
}