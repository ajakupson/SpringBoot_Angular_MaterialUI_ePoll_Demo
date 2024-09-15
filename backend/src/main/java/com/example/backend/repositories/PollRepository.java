package com.example.backend.repositories;

import com.example.backend.entities.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PollRepository extends JpaRepository<Poll, Long> {
    Optional<Poll> findById(Long id);
    Optional<Poll> findByTitle(String title);
}
