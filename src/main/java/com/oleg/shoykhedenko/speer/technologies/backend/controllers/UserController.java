package com.oleg.shoykhedenko.speer.technologies.backend.controllers;

import com.oleg.shoykhedenko.speer.technologies.backend.dto.UserDto;
import com.oleg.shoykhedenko.speer.technologies.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody UserDto user){
        System.out.println(user);
        userService.registerUser(user);
    }
}
