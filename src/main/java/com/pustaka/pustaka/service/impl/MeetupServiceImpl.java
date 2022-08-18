package com.pustaka.pustaka.service.impl;

import com.pustaka.pustaka.model.Meetup;
import com.pustaka.pustaka.payload.MeetupDto;
import com.pustaka.pustaka.repository.MeetupRepository;
import com.pustaka.pustaka.service.MeetupService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeetupServiceImpl implements MeetupService {
    private MeetupRepository meetupRepository;
    private ModelMapper modelMapper;

    public MeetupServiceImpl(MeetupRepository meetupRepository,
                             ModelMapper modelMapper
                             ) {
        this.meetupRepository = meetupRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public MeetupDto createMeetup(MeetupDto meetupDto) {
        Meetup meetup = meetupRepository.save(Meetup.builder()
                        .title(meetupDto.getTitle())
                        .image(meetupDto.getImage())
                        .address(meetupDto.getAddress())
                        .description(meetupDto.getDescription())
                .build());
        return modelMapper.map(meetup, MeetupDto.class);
    }

    @Override
    public List<MeetupDto> listMeetup() {
        List<Meetup> listMeetup = meetupRepository.findAll();
        return listMeetup.stream().map(meetup -> modelMapper.map(meetup, MeetupDto.class))
                .collect(Collectors.toList());
    }
}
