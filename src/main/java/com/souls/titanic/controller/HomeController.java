package com.souls.titanic.controller;

import com.souls.titanic.service.PassengerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileNotFoundException;

@Controller
@RequestMapping("/")
public class HomeController {
    private final PassengerService passengerService;

    public HomeController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping
    public String main(Model model) throws FileNotFoundException {
        return "home";
    }
}
