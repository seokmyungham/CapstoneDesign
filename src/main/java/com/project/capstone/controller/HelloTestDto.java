package com.project.capstone.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HelloTestDto {

    private String name;
    private int age;
    private int number;
    private String message;

    public HelloTestDto(String name, int age, int number, String message) {
        this.name = name;
        this.age = age;
        this.number = number;
        this.message = message;
    }
}
