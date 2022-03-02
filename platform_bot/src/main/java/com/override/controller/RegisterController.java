package com.override.controller;

import com.override.services.JoinRequestService;
import dtos.ResponseJoinRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private JoinRequestService requestService;

    @PostMapping
    public void responseForRequest(@RequestBody ResponseJoinRequestDTO responseDTO) {
        requestService.processJoinRequestResponse(responseDTO);
    }
}
