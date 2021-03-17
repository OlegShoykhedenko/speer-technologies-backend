package com.oleg.shoykhedenko.speer.technologies.backend.repositories;

import com.oleg.shoykhedenko.speer.technologies.backend.entities.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
}
