package com.femcoders.authentication.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/user")
    @ResponseBody
    public String user() {
        return "Welcome, User!";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin() {
        return "Welcome, Admin!";
    }

    @GetMapping("/")
    @ResponseBody
    public String home() {
        return "Home page";
    }
}
