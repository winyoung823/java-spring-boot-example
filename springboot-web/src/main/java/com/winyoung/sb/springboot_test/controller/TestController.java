package com.winyoung.sb.springboot_test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    
    @GetMapping("/Test")
    public String Test(){
        return "Hello";
    }
}
