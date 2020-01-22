package com.nb.backend.controller;

import com.nb.backend.entity.User;
import com.nb.backend.model.JwtUser;
import com.nb.backend.security.JwtGenerator;
import com.nb.backend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
//solo dar acceso al dominio adecuado, para esta caso se da acceso a todos
@CrossOrigin(origins = "*", methods = {RequestMethod.POST})
@RequestMapping("/auth")
public class UserRestController {

    @Autowired
    private IUserService userService;

    @Autowired
    private JwtGenerator jwtGenerator;

    @PostMapping(value = "/user")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        if (userService.findUser(user) == null) {
            userService.save(user);
            User userFromDB = userService.checkUserLogin(user);
            JwtUser jwtUser = new JwtUser();
            jwtUser.setId(userFromDB.getId());
            jwtUser.setUsername(userFromDB.getEmail());

            return new ResponseEntity<>((Collections.singletonMap("jwtToken", jwtGenerator.generate(jwtUser))), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping(value = "/auth")
    public ResponseEntity<?> login(@RequestBody User user) {
        User userFromDB = userService.checkUserLogin(user);
        if (userFromDB != null) {
            //Generar Token
            JwtUser jwtUser = new JwtUser();
            jwtUser.setId(userFromDB.getId());
            jwtUser.setUsername(userFromDB.getEmail());

            return new ResponseEntity<>((Collections.singletonMap("jwtToken", jwtGenerator.generate(jwtUser))), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }
}
