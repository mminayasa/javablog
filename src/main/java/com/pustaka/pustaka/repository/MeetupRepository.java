package com.pustaka.pustaka.repository;

import com.pustaka.pustaka.model.Meetup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetupRepository extends JpaRepository<Meetup, Long> {
}
