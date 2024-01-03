package com.project.shopApp.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//su dung token de xac thuc cac yeu cau trong cac API khac trong ung dung
// Sử dụng bộ lọc de kiem tra ca xác thực token trong mỗi yêu cầu
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
// sử dụng này để mỗi request một nó sẽ đi qua thàng này để kiểm tra

    private final JwtUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    public JwtRequestFilter(JwtUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            final String authorizationHeader = request.getHeader("Authorization");

            String jwt = (!StringUtils.isEmpty(authorizationHeader) && authorizationHeader.startsWith("Bearer "))
                            ? authorizationHeader.substring(7)
                            : null;

            String username = null;

            if (!StringUtils.isEmpty(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
                try {
                     username = jwtUtil.extractUsername(jwt);
                } catch (ExpiredJwtException e) {
                    throw new IOException("Username not found!!!  You are stupid");
                    // Token hết hạn
                }
            }
//            else {
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//                return;
//            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // doan này ta gọi UserDetails cua java spring nen khi getAuthorities ở duoi no se lay authoririties
                // cua java spring nen ta can dổi sang của user đẻ lấy được authorities
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
//            else {
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//                return;
//            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }
}
