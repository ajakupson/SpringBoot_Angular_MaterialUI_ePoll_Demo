package com.example.backend.services;

import com.example.backend.entities.PollOption;
import com.example.backend.exceptions.AppException;
import com.example.backend.repositories.PollOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PollsOptionsService {
    private final PollOptionRepository pollOptionRepository;

    public PollOption getPollOptionById(Long id) {
        Optional<PollOption> oPollOption = pollOptionRepository.findById(id);
        if (oPollOption.isEmpty()) {
            throw new AppException("Poll option with given id doesnt exist", HttpStatus.NOT_FOUND);
        }

        return oPollOption.get();
    }

    public PollOption savePollOption(PollOption pollOption) {
        return pollOptionRepository.save(pollOption);
    }
}
