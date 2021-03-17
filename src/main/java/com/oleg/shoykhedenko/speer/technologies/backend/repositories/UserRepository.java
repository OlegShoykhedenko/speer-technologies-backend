package com.oleg.shoykhedenko.speer.technologies.backend.repositories;

import com.oleg.shoykhedenko.speer.technologies.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByUsername(String username);
}
