package com.example.backend.repositories;

import com.example.backend.entities.PollOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PollOptionRepository extends JpaRepository<PollOption, Long> {
    Optional<PollOption> findById(Long id);
    Optional<PollOption> findByPollId(Long id);
}
