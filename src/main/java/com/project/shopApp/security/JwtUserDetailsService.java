package com.project.shopApp.security;

import com.project.shopApp.domain.Role;
import com.project.shopApp.domain.User;
import com.project.shopApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

/// Xác thực nguời dùng

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findOneByLogin(login).orElseThrow(() -> new RuntimeException("User not found with username: " + login));

//        Collection<GrantedAuthority> rolesList = new ArrayList<>();
//        rolesList.add(user.getRole().getName());

        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPasswordHard(), user.getAuthorities());
    }

//    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String login, User user){
//        if (user.ge)
//
//            return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPasswordHard(), )
//    }
}
