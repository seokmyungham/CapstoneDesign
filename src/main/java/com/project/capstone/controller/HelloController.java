package com.project.capstone.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public HelloTestDto helloDto() {
        HelloTestDto helloTestDto = new HelloTestDto("완자", 25, 6018, "Hello");
        return helloTestDto;
    }
}
