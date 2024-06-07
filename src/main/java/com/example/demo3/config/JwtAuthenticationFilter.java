package com.example.demo3.config;

//import io.jsonwebtoken.*;

import com.example.demo3.models.*;
import com.example.demo3.repos.*;
import com.example.demo3.services.JwtService;
import com.example.demo3.services.EmployeeService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        String jwtToken="";
        final String userEmail;
        System.out.println("entered the filter:");
        System.out.println("\tauthHeader: "+authHeader);
        
        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            if(request.getCookies()!=null){
                for(Cookie c : request.getCookies()){
                    if(c.getName().equals("jwt")) jwtToken = c.getValue();
                }
            }
        }else if(authHeader!=null) jwtToken = authHeader.substring(7);

        if(jwtToken.equals(""))
        {
            
            System.out.println("--authHeader is null:"+(authHeader==null));
            filterChain.doFilter(request,response);
            return;
        }

        userEmail = jwtService.extractUsername(jwtToken);
        System.out.println("probably extracted userEmail:"+userEmail);
        if(userEmail!=null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if(jwtService.isTokenValid(jwtToken,userDetails)){
                System.out.println("----The token is valid----");

                for (GrantedAuthority authority : userDetails.getAuthorities()) {
                    if (authority.getAuthority().equals("STUDENT")) {
                        System.out.println("auth is Student   "+authority.getAuthority());
                        break;
                    }
                    System.out.println("auth is not Student   "+authority.getAuthority());
                }

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }

}