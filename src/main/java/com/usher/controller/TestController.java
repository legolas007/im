package com.usher.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Usher
 * @Description:
 */
@RestController
public class TestController {
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
