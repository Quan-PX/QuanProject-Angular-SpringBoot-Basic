package com.project.shopApp.service;

import com.project.shopApp.domain.User;
import com.project.shopApp.exception.PermissionException;
import com.project.shopApp.web.rest.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {
    User register(UserDTO userDTO);

    User login(UserDTO userDTO);

    User updateUser(Long id, UserDTO userDTO) throws PermissionException;

    void removeUser(Long id);

    User getUserById(Long id);

    Page<User> getAllUser(Pageable pageable);
}
