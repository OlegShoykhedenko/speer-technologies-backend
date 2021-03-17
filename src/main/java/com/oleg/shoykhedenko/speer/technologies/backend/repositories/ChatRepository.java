package com.oleg.shoykhedenko.speer.technologies.backend.repositories;

import com.oleg.shoykhedenko.speer.technologies.backend.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
}
