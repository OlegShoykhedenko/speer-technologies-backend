package com.oleg.shoykhedenko.speer.technologies.backend.controllers;

import com.oleg.shoykhedenko.speer.technologies.backend.dto.TweetDto;
import com.oleg.shoykhedenko.speer.technologies.backend.entities.Tweet;
import com.oleg.shoykhedenko.speer.technologies.backend.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tweet")
@RequiredArgsConstructor
public class TweetController {

    private final TweetService tweetService;

    @PostMapping("make-tweet")
    @ResponseStatus(HttpStatus.CREATED)
    public void makeTweet(@RequestBody TweetDto tweetDto) {
        tweetService.makeTweet(tweetDto);
    }

    @GetMapping("read-tweet/{tweetId}")
    @ResponseStatus(HttpStatus.OK)
    public Tweet getTweet(@PathVariable Long tweetId) {
        return tweetService.getTweet(tweetId);
    }

    @PatchMapping("update-tweet/{tweetId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateTweet(@PathVariable Long tweetId, @RequestBody TweetDto tweetDto) {
        tweetService.updateTweet(tweetId, tweetDto);
    }

    @DeleteMapping("delete-tweet/{tweetId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTweet(@PathVariable Long tweetId, @RequestBody TweetDto tweetDto) {
        tweetService.deleteTweet(tweetId, tweetDto);
    }

}
