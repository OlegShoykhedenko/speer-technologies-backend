package com.oleg.shoykhedenko.speer.technologies.backend.dto;

import lombok.Data;

@Data
public class ChatDto {
    private String message;
    private Long firstUserId;
    private Long secondUserId;
    private Long fromUserId;
}
