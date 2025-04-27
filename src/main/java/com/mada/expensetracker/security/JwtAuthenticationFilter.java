package com.mada.expensetracker.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mada.expensetracker.service.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetails;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, CustomUserDetailsService customUserDetails) {
        this.jwtUtil = jwtUtil;
        this.customUserDetails = customUserDetails;
    }

    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

                String authHeader = request.getHeader("Authorization");

                System.out.println("Auth Header: " + authHeader);
                
                if (authHeader != null && authHeader.startsWith("Bearer")) {
                    String token = authHeader.substring(7);
                    String email = jwtUtil.extractUsername(token);

                    if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails userDetails = customUserDetails.loadUserByUsername(email);
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
                
                filterChain.doFilter(request, response);
    }

}
