package com.nb.backend.controller;

import com.nb.backend.entity.Task;
import com.nb.backend.model.JwtUser;
import com.nb.backend.security.JwtValidator;
import com.nb.backend.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.xml.xpath.XPath;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Objects;

@RestController
//solo dar acceso al dominio adecuado, para esta caso se da acceso a todos
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.POST})
@RequestMapping("/api")
public class TaskRestController {

    public static final String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources";

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

    @GetMapping("/task/list")
    public ResponseEntity<?> getTask(@RequestHeader(name = "Authorization") String bearerToken) {
        String token = bearerToken.substring(7);
        JwtUser jwtUser = validator.validate(token);
        List<Task> taskList = taskService.getTaskUser(jwtUser.getId());
        if (taskList != null && taskList.size() != 0) {
            return new ResponseEntity<>(taskList, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable(value = "id") Long id) {

        taskService.deleteTask(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("/task/upload")
    public ResponseEntity<?> createTaskImage(@RequestParam("image") MultipartFile file,
                                             @RequestParam("name") String name,
                                             @RequestParam("description") String desc,
                                             @RequestHeader(name = "Authorization") String bearerToken) {
        Task task = new Task();
        String token = bearerToken.substring(7);
        JwtUser jwtUser = validator.validate(token);
        task.setUserId(jwtUser.getId());
        task.setStatus("to-do");
        task.setName(name);
        task.setDescription(desc);

        //Guardar Imagen
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path path = Paths.get(UPLOAD_DIRECTORY, fileName);

        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Compone la direccion de url con el nombre del dominio
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(fileName)
                .toUriString();

        task.setImageUrl(fileDownloadUri);

        //guardar la task
        taskService.saveTask(task);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

}
