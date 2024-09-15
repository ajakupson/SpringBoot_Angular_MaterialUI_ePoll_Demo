package com.example.backend.dtos;

public record PollOptionDto(
        Long id,
        String title,
        int votes) { }
