package com.nb.backend.controller;

import com.nb.backend.entity.User;
import com.nb.backend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserRestController {

    @Autowired
    private IUserService userService;

    @PostMapping(value = "/user")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        if (userService.findUser(user) == null) {
            userService.save(user);
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
    }

}
