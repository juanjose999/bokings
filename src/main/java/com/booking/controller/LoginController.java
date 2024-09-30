package com.booking.controller;

import com.booking.service.impl.CustomerService;
import com.booking.service.impl.HotelService;
import com.booking.service.jwt.JwtService;
import com.booking.service.request.LoginForm;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final JwtService jwtService;
    private final ApplicationContext context;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/customer")
    public ResponseEntity<String> loginCustomer(@RequestBody LoginForm loginForm){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginForm.username(),loginForm.password()));
        if(authentication.isAuthenticated()){
            return ResponseEntity.ok(jwtService.generateJwt(context.getBean(CustomerService.class).loadUserByUsername(loginForm.username())));
        }else{
            throw new BadCredentialsException("User not exist in data base");
        }

    }

    @PostMapping("/hotel")
    public ResponseEntity<String> loginHotel(@RequestBody LoginForm loginForm){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginForm.username(),loginForm.password()));
        if(authentication.isAuthenticated()){
            return ResponseEntity.ok(jwtService.generateJwt(context.getBean(HotelService.class).loadUserByUsername(loginForm.username())));
        }else{
            throw new BadCredentialsException("Hotel not exist in data base");
        }

    }
}
