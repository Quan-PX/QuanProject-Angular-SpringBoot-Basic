package com.project.shopApp.security;

import com.project.shopApp.web.rest.dto.LoginDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Tạo một lớp JwtAuthenticationController để xử lý yêu cầu xác thực và phát hành token:

@RestController
@RequestMapping("/api")
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final JwtUserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    public JwtAuthenticationController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, JwtUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody LoginDTO loginDTO) throws Exception {
        try {
            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getUsername());

            // Check cả pass word nữa.
            if(!passwordEncoder.matches(loginDTO.getPassword(), userDetails.getPassword())){
                throw new BadCredentialsException("Wrong phone number or password!!!. PASSWORD =))");
            }

            // de authenticate voi hethong
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getUsername(),
                            loginDTO.getPassword(),
                            userDetails.getAuthorities()
                    ));

//            UsernamePasswordAuthenticationToken authenticationToken =
//                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
//
//            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);



            final String token = jwtUtil.genarateToken(userDetails);
            return ResponseEntity.ok(new JwtToken(token));

        } catch (AuthenticationException e){
            throw new Exception("Incorrect username or password. DEO THAY nguoi dung", e);
        }
    }

    static class JwtToken {
        private String token;

        public JwtToken(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

}


