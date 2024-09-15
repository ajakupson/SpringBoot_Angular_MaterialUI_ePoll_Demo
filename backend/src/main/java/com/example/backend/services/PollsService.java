package com.example.backend.services;

import com.example.backend.entities.Poll;
import com.example.backend.entities.PollOption;
import com.example.backend.entities.VoteLog;
import com.example.backend.exceptions.AppException;
import com.example.backend.repositories.PollRepository;
import com.example.backend.repositories.VotesLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class PollsService {

    private final PollRepository pollRepository;
    private final PollsOptionsService pollsOptionsService;
    private final VotesLogRepository votesLogRepository;

    public List<Poll> getListOfPolls() {
        return pollRepository.findAll();
    }
    public Poll getPollById(Long id) {

        Optional<Poll> oPoll = pollRepository.findById(id);
        if (oPoll.isEmpty()) {
            throw new AppException("Poll with given id doesnt exist", HttpStatus.NOT_FOUND);
        }

        return oPoll.get();
    }

    public boolean hasAlreadyVoted(Long pollId, String ipAddress) {
        return votesLogRepository.existsByPollIdAndIpAddress(pollId, ipAddress);
    }

    public Poll pollVote(Long pollId, Long optionId, String ipAddress, String userAgent) {
        Poll poll = getPollById(pollId);
        PollOption pollOption = pollsOptionsService.getPollOptionById(optionId);

        pollOption.setVotes(pollOption.getVotes() + 1);
        PollOption savedPollOption = pollsOptionsService.savePollOption(pollOption);

        List<PollOption> pollOptions = poll.getOptions();
        IntStream.range(0, pollOptions.size())
                .filter(i -> Objects.equals(pollOptions.get(i).getId(), savedPollOption.getId()))
                .findFirst()
                .ifPresent(i -> pollOptions.set(i, savedPollOption));

        poll.setOptions(pollOptions);

        recordVote(poll, savedPollOption, ipAddress, userAgent);

        return poll;
    }

    public Poll addPoll(Poll poll) {
        if (poll.getOptions() != null) {
            poll.getOptions().forEach(option -> option.setPoll(poll));
        }

        return pollRepository.save(poll);
    }

    private void recordVote(Poll poll, PollOption pollOption, String ipAddress, String userAgent) {
        VoteLog voteLog = new VoteLog();
        voteLog.setPoll(poll);
        voteLog.setOption(pollOption);
        voteLog.setIpAddress(ipAddress);
        voteLog.setUserAgent(userAgent);
        votesLogRepository.save(voteLog);
    }
}
