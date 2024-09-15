package com.example.backend.mappers;

import com.example.backend.dtos.PollDto;
import com.example.backend.entities.Poll;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PollMapper {

    @Mapping(target = "options", source = "options")
    PollDto toPollDto(Poll poll);

    @Mapping(target = "options", source = "options")
    Poll dtoToPoll(PollDto pollDto);
}
