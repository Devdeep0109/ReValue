package com.reselling.Book.config;

import com.reselling.Book.service.JwtService;
import com.reselling.Book.service.customerService.MyUserDetailsService;
import com.reselling.Book.service.sellerService.MySellerDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Order(0)
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private MySellerDetailsService sellerDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {


        // Allow OPTIONS requests (preflight for multipart)
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;
        String role = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            email = jwtService.extractUserName(token);
            role = jwtService.extractRole(token);    // this returns email
        }

        // Authenticate only if no current authentication exists
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails;

            // ---- PICK SERVICE BASED ON ROLE ----
            if (role.equals("USER")) {
                userDetails = userDetailsService.loadUserByUsername(email);
            } else if (role.equals("SELLER")) {
                userDetails = sellerDetailsService.loadUserByUsername(email);
            } else {
                filterChain.doFilter(request, response);
                return;
            }

            if (jwtService.validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }

        filterChain.doFilter(request, response);
    }
}
