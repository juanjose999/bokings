package com.booking.config;

import com.booking.service.impl.CustomerService;
import com.booking.service.impl.HotelService;
import com.booking.service.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final ApplicationContext context;
    private final HotelService hotelService;
    private final CustomerService customerService;

    public JwtFilter(JwtService jwtService, ApplicationContext context, HotelService hotelService, CustomerService customerService) {
        this.jwtService = jwtService;
        this.context = context;
        this.hotelService = hotelService;
        this.customerService = customerService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authentication = request.getHeader("Authorization");
        if(authentication == null || !authentication.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        String jwt = authentication.substring(7);
        String username = jwtService.extractUsername(jwt);

        if(username!=null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails;
            try{
                userDetails = customerService.loadUserByUsername(username);
            }catch (Exception e){
                userDetails = hotelService.loadUserByUsername(username);
            }
            if(userDetails!=null && jwtService.isValidToken(jwt)){
                UsernamePasswordAuthenticationToken token =
                        new UsernamePasswordAuthenticationToken(username,userDetails.getPassword(),userDetails.getAuthorities());

                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(token);

            }
        }
        filterChain.doFilter(request,response);
    }
}
