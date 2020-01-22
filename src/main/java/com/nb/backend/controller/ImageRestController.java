package com.nb.backend.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class ImageRestController {

    @GetMapping(value = "/download/{image}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void getImage(HttpServletResponse response, @PathVariable(value = "image") String image) {

        ClassPathResource imgFile = new ClassPathResource("/" + image);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        try {
            StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
