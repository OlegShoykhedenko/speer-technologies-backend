package com.oleg.shoykhedenko.speer.technologies.backend.dto;

import lombok.Data;

@Data
public class TweetDto {
    private String message;
    private Long userId;
}
