package com.pustaka.pustaka.service;

import com.pustaka.pustaka.payload.MeetupDto;
import java.util.List;

public interface MeetupService {
    MeetupDto createMeetup(MeetupDto meetupDto);
    List<MeetupDto> listMeetup();
}
