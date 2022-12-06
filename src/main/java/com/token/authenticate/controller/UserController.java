package com.token.authenticate.controller;

import com.token.authenticate.domain.dto.UserJoinRequest;
import com.token.authenticate.domain.dto.UserLoginRequest;
import com.token.authenticate.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserJoinRequest userJoinRequest) {
        log.info("name :{} password :{}",userJoinRequest.getUserName(),userJoinRequest.getPassword());
        String result = userService.join(userJoinRequest);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest userLoginRequest) {
        String token = userService.login(userLoginRequest);
        return ResponseEntity.ok().body(token);
    }
}
