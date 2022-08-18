package com.pustaka.pustaka.payload;

import lombok.Data;

@Data
public class MeetupDto {
    private long id;
    private String title;
    private String image;
    private String address;
    private String description;
}
