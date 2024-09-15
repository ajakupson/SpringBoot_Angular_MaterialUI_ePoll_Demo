package com.example.backend.mappers;

import com.example.backend.dtos.PollOptionDto;
import com.example.backend.entities.PollOption;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PollOptionMapper.class})
public interface PollOptionMapper {
    PollOptionDto pollOptionToDto(PollOption option);

    PollOption dtoToPollOption(PollOptionDto pollOptionDto);
}
