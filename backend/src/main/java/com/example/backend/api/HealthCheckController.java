package com.example.backend.api;


import com.example.backend.dtos.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthCheckController {

    @GetMapping("health_check")
    public ResponseEntity<ResponseDto> checkHealth() {

        ResponseDto responseDto = ResponseDto.builder()
                .isSuccess(true)
                .message("API is working!")
                .data(null)
                .build();

        return ResponseEntity.ok(responseDto);

    }


}
