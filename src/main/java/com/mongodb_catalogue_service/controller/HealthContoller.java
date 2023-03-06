package com.mongodb_catalogue_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthContoller {

    @GetMapping("/health")
   String checkHealth()
    {
        return "alive";
    }
}
