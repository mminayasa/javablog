package com.pustaka.pustaka.repository;

import com.pustaka.pustaka.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
