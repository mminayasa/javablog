package com.pustaka.pustaka.service;

import com.pustaka.pustaka.payload.PostDto;
import com.pustaka.pustaka.payload.PostResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPostById(long id);
    PostDto updatePost(PostDto postDto, long id);
    void deletePostById(long id);
}
