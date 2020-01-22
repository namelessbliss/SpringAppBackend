package com.nb.backend.controller;

import com.nb.backend.entity.Task;
import com.nb.backend.model.JwtUser;
import com.nb.backend.security.JwtValidator;
import com.nb.backend.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TaskRestController {

    @Autowired
    private ITaskService taskService;

    @Autowired
    private JwtValidator validator;

    @PostMapping("/task")
    public ResponseEntity<?> createTask(@RequestBody Task task, @RequestHeader(name = "Authorization") String bearerToken) {

        String token = bearerToken.substring(7);
        JwtUser jwtUser = validator.validate(token);
        task.setUserId(jwtUser.getId());
        task.setStatus("to-do");
        taskService.saveTask(task);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PutMapping("/task")
    public ResponseEntity<?> updateTask(@RequestBody Task task, @RequestHeader(name = "Authorization") String bearerToken) {
        Task tastUpdate = taskService.findByIdSQL(task.getId());
        tastUpdate.setStatus(task.getStatus());
        taskService.saveTask(tastUpdate);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
