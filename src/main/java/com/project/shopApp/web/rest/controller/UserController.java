package com.project.shopApp.web.rest.controller;

import com.project.shopApp.domain.User;
import com.project.shopApp.exception.PermissionException;
import com.project.shopApp.repository.UserRepository;
import com.project.shopApp.service.UserService;
import com.project.shopApp.web.rest.dto.LoginDTO;
import com.project.shopApp.web.rest.dto.UserDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

//    http://localhost:8080/api/user
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers( @RequestParam("limit") int limit, @RequestParam("page") int page){
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("createdAt").descending());

        Page<User> pageUser = userService.getAllUser(pageRequest);
        int totalPages = pageUser.getTotalPages();

        List<User> listUser = pageUser.getContent();

        return ResponseEntity.ok(listUser);
    }


//    @PostMapping("/login")
//    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO){
//
//
//        return null;
//    }


    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO){
        if (userDTO.getId() != null){
            throw new RuntimeException("A new user cannot already have an ID");
        } else if (userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).isPresent()) {
            throw new RuntimeException("Login name already used!");
        } else {
            User newUser = userService.register(userDTO);
            return ResponseEntity.ok(newUser);
        }
    }


    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser( @PathVariable("id") long id ,@Valid @RequestBody UserDTO userDTO) throws PermissionException {
        User updateUser = userService.updateUser(id, userDTO);

        if(updateUser == null) {
            throw new PermissionException("You update your user so stupid");
        }
        return ResponseEntity.ok(updateUser);
    }


    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id){
        userService.removeUser(id);
        return ResponseEntity.ok("DELETE USER SUCCESS!!!");
    }
}
