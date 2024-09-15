package com.example.backend.dtos;

import java.util.List;

public record PollDto(
        Long id,
        String title,
        List<PollOptionDto> options
) { }
