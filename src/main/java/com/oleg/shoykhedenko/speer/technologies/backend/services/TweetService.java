package com.oleg.shoykhedenko.speer.technologies.backend.services;

import com.oleg.shoykhedenko.speer.technologies.backend.dto.TweetDto;
import com.oleg.shoykhedenko.speer.technologies.backend.entities.Tweet;
import com.oleg.shoykhedenko.speer.technologies.backend.repositories.TweetRepository;
import com.oleg.shoykhedenko.speer.technologies.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TweetService {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;


    public void makeTweet(TweetDto tweetDto) {
        var user = userRepository.findById(tweetDto.getUserId())
                .orElseThrow(IllegalArgumentException::new);
        Tweet tweet = Tweet.builder()
                .message(tweetDto.getMessage())
                .user(user)
                .build();
        tweetRepository.save(tweet);
    }

    public Tweet getTweet(Long tweetId) {
        return tweetRepository.findById(tweetId)
                .orElseThrow(IllegalArgumentException::new);
    }

    public void updateTweet(Long tweetId, TweetDto tweetDto) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(IllegalArgumentException::new);

        if (!tweetDto.getUserId().equals(tweet.getId())) {
            throw new IllegalCallerException();
        }

        tweet.setMessage(tweetDto.getMessage());
        tweetRepository.save(tweet);
    }

    public void deleteTweet(Long tweetId, TweetDto tweetDto) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(IllegalArgumentException::new);

        if (!tweetDto.getUserId().equals(tweet.getId())) {
            throw new IllegalCallerException();
        }

        tweetRepository.delete(tweet);
    }
}
