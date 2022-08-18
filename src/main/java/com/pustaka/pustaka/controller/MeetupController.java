package com.pustaka.pustaka.controller;

import com.pustaka.pustaka.payload.MeetupDto;
import com.pustaka.pustaka.service.MeetupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meetups")
@CrossOrigin(origins = "http://localhost:3000")
public class MeetupController {
    private MeetupService meetupService;

    public MeetupController(MeetupService meetupService) {
        this.meetupService = meetupService;
    }

    @PostMapping
    public ResponseEntity<MeetupDto> createMeetup(@RequestBody MeetupDto request) {
        return new ResponseEntity<>(meetupService.createMeetup(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MeetupDto>> getAllMeetup() {
        return new ResponseEntity<>(meetupService.listMeetup(), HttpStatus.OK);
    }
}
