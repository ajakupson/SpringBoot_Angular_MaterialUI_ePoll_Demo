package com.example.backend.repositories;

import com.example.backend.entities.VoteLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface VotesLogRepository extends JpaRepository<VoteLog, Long> {
    boolean existsByPollIdAndIpAddress(Long pollId, String ipAddress);
}
