package ru.astinezh.usermanager.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import ru.ASTiNEZh.controller.AccountApi;
import ru.ASTiNEZh.dto.UserLoginDTO;
import ru.ASTiNEZh.dto.UserLoginResponseDTO;
import ru.ASTiNEZh.dto.UserRegisterDTO;
import ru.astinezh.usermanager.service.UserManagerService;

@RestController
public class UserManagerController implements AccountApi {

    @Autowired
    UserManagerService userManagerService;

    @Override
    public ResponseEntity<UserLoginResponseDTO> login(@Valid UserLoginDTO userLoginDTO) {
        return ResponseEntity.ok(userManagerService.login(userLoginDTO.getEmail(), userLoginDTO.getPassword()));
    }

    @Override
    public ResponseEntity<String> registerUser(@Valid UserRegisterDTO userRegisterDTO) {
        return ResponseEntity.ok(userManagerService.registerNewUser(userRegisterDTO));
    }
}
