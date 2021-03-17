package com.oleg.shoykhedenko.speer.technologies.backend.services;

import com.oleg.shoykhedenko.speer.technologies.backend.dto.UserDto;
import com.oleg.shoykhedenko.speer.technologies.backend.entities.User;
import com.oleg.shoykhedenko.speer.technologies.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void registerUser(UserDto userDto){
        var user = User.builder()
                .username(userDto.getUsername())
                .password(new BCryptPasswordEncoder().encode(userDto.getPassword()))
                .build();
        userRepository.save(user);
    }

    public User findByLogin(String username) {
        return userRepository.findById(1L).get();
    }
}
