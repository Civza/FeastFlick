package com.feastflick.controller;

import com.feastflick.model.User;
import com.feastflick.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> findUserbyToken(@RequestHeader("Authorization") String token) throws Exception{
        User user = userService.findUserByToken(token);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
