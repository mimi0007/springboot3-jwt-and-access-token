package com.ownlearning.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secured")
@RequiredArgsConstructor
public class SecuredController {

    @GetMapping("/demo")
    public String demo() {
        return "Secured with JWT!";
    }
}
