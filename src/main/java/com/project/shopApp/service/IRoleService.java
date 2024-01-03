package com.project.shopApp.service;

import com.project.shopApp.domain.Role;
import com.project.shopApp.web.rest.dto.RoleDTO;

import java.util.List;

public interface IRoleService {

    Role createRole(RoleDTO roleDTO);

    List<Role> getAllRole();

    void deleteRole(Long id);

    Role updateRole(RoleDTO roleDTO, Long id);

    Role getRoleById(Long id);
}
