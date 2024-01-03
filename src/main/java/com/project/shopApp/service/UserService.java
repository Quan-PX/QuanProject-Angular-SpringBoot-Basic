package com.project.shopApp.service;

import com.project.shopApp.domain.Role;
import com.project.shopApp.domain.User;
import com.project.shopApp.exception.PermissionException;
import com.project.shopApp.repository.RoleRepository;
import com.project.shopApp.repository.UserRepository;
import com.project.shopApp.web.rest.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@Transactional
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User register(UserDTO userDTO) {
        User user = new User();
        String encoderPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setLogin(userDTO.getLogin().trim());
        user.setEmail(userDTO.getEmail().trim());
        user.setFirstName(userDTO.getFirstName().trim());
        user.setLastName(userDTO.getLastName().trim());
        user.setActivated(true);
        user.setCreatedAt(Instant.now());
        user.setPasswordHard(encoderPassword);
        user.setImageUrl(userDTO.getImageUrl());

        Role setRoleUser = roleRepository.findOneByName(Role.USER).orElseThrow(() -> new RuntimeException("role is not found"));
        user.setRole(setRoleUser);

        userRepository.save(user);
//        this.clearUserCaches(user);
        return userRepository.save(user);
    }

    @Override
    public User login(UserDTO userDTO) {

        return null;
    }

    @Override
    public User updateUser(Long id, UserDTO userDTO) throws PermissionException {
        User updateUser = getUserById(id);

        Role role = roleRepository.findById(userDTO.getRole()).orElseThrow(() -> new RuntimeException("Can not find roleId"));
        if(role.getName().toUpperCase().equals(Role.AMIN)){
            throw new PermissionException("You cannot update user an admin account");
        }

        if(updateUser != null) {
            updateUser.setFirstName(userDTO.getFirstName());
            updateUser.setLastName(userDTO.getLastName());
            updateUser.setEmail(userDTO.getEmail());
            updateUser.setEmail(userDTO.getEmail());
            updateUser.setImageUrl(userDTO.getImageUrl());
            updateUser.setRole(role);
            updateUser.setActivated(userDTO.isActivated());
            return userRepository.save(updateUser);
        }

        return null;
    }

    @Override
    public void removeUser(Long id) {
        Optional<User> existingUser = userRepository.findById(id);
        existingUser.ifPresent(user -> userRepository.delete(user));
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Can not find UserId"));
    }

    @Override
    public Page<User> getAllUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
