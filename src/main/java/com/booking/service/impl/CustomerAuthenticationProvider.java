package com.booking.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerAuthenticationProvider implements AuthenticationProvider {

    private final CustomerService customerService;
    private final HotelService hotelService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        UserDetails userDetails;
        try{
            userDetails = customerService.loadUserByUsername(username);
        }catch (Exception e){
            userDetails = hotelService.loadUserByUsername(username);
        }
        if(userDetails != null){
            return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        }else{
            throw new BadCredentialsException("Entity not found in db");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
