package com.example.backend.api;

import com.example.backend.dtos.PollDto;
import com.example.backend.dtos.ResponseDto;
import com.example.backend.entities.Poll;
import com.example.backend.mappers.PollMapper;
import com.example.backend.services.PollsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/polls")
public class PollsController {

    private final PollsService pollsService;
    private final PollMapper pollMapper;

    @GetMapping
    public ResponseEntity<ResponseDto> getListOfPolls() {
        List<Poll> polls = pollsService.getListOfPolls();
        List<PollDto> pollsDto = new ArrayList<>();
        polls.forEach(p -> {
            pollsDto.add(new PollDto(p.getId(), p.getTitle(), List.of()));
        });

        ResponseDto responseDto = ResponseDto.builder()
                .isSuccess(true)
                .message(null)
                .data(pollsDto)
                .build();

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getPollWithOptions(@PathVariable Long id) {
       Poll poll = pollsService.getPollById(id);
       PollDto pollDto = pollMapper.toPollDto(poll);

        ResponseDto responseDto = ResponseDto.builder()
                .isSuccess(true)
                .message(null)
                .data(pollDto)
                .build();

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/{id}/vote/{option}")
    public ResponseEntity<ResponseDto> pollVote(
            @PathVariable Long id,
            @PathVariable Long option,
            HttpServletRequest request
    ) {
        String ipAddress = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

        Poll poll = pollsService.pollVote(id, option, ipAddress, userAgent);
        PollDto pollDto = pollMapper.toPollDto(poll);

        ResponseDto responseDto = ResponseDto.builder()
                .isSuccess(true)
                .message(null)
                .data(pollDto)
                .build();

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseDto> addPoll(@RequestBody PollDto pollDto) {
        Poll poll = pollMapper.dtoToPoll(pollDto);
        Poll savedPoll = pollsService.addPoll(poll);

        PollDto savedPollDto = pollMapper.toPollDto(savedPoll);

        ResponseDto responseDto = ResponseDto.builder()
                .isSuccess(true)
                .message(null)
                .data(savedPollDto)
                .build();

        return ResponseEntity.ok(responseDto);
    }
}
