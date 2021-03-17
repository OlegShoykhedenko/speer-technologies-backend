package com.oleg.shoykhedenko.speer.technologies.backend.services;

import com.oleg.shoykhedenko.speer.technologies.backend.dto.ChatDto;
import com.oleg.shoykhedenko.speer.technologies.backend.entities.Chat;
import com.oleg.shoykhedenko.speer.technologies.backend.entities.User;
import com.oleg.shoykhedenko.speer.technologies.backend.repositories.ChatRepository;
import com.oleg.shoykhedenko.speer.technologies.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;


    public void sendMessage(ChatDto chatDto) {
        User firstUser = userRepository.findById(chatDto.getFirstUserId())
                .orElseThrow(IllegalArgumentException::new);
        User secondUser = userRepository.findById(chatDto.getSecondUserId())
                .orElseThrow(IllegalArgumentException::new);
        User fromUser = firstUser.getId().equals(chatDto.getFromUserId()) ? firstUser : secondUser;

        var chat = Chat.builder()
                .firstUser(firstUser)
                .secondUser(secondUser)
                .message(chatDto.getMessage())
                .fromUser(fromUser)
                .build();
        chatRepository.save(chat);
    }
}
