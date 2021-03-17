package com.oleg.shoykhedenko.speer.technologies.backend.controllers;

import com.oleg.shoykhedenko.speer.technologies.backend.dto.ChatDto;
import com.oleg.shoykhedenko.speer.technologies.backend.services.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping(value = "send-message")
    @ResponseStatus(HttpStatus.OK)
    public void sendMessage(@RequestBody ChatDto chatDto) {
        chatService.sendMessage(chatDto);
    }
}
