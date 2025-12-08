//package com.auth.service;
//
//import com.auth.dto.LoginRequestDto;
//import com.auth.model.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class AuthService {
//
//    @Autowired
//    private UserService userService;
//
//    Optional<String> authenticateAndGenerateToken(LoginRequestDto loginRequestDto){
//        Optional<String> token  = userService.findByEmail(loginRequestDto.getEmail())
//                .filter(u -> paswordEncoder.matches(loginRequestDto.getPassword(), u.getPassword()))
//                .map(u -> jwtUtil.generateToken(u.getEmail(), u.getRole()));
//
//        return token;
//    }
//}
