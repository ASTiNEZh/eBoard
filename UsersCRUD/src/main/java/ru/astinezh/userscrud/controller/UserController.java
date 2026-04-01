package ru.astinezh.userscrud.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.ASTiNEZh.controller.UsersApi;
import ru.ASTiNEZh.dto.UserDTO;
import ru.astinezh.userscrud.service.UserService;

import java.util.UUID;

@RestController
public class UserController implements UsersApi {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<Void> createUser(UserDTO userDTO) {
        userService.create(userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteUser(UUID uuid) {
        userService.delete(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateUser(UUID uuid, UserDTO userDTO) {
        userService.update(uuid, userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserDTO> getUser(UUID uuid) {
        return ResponseEntity.ok(userService.findById(uuid));
    }
}
