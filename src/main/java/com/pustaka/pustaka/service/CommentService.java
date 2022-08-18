package com.pustaka.pustaka.service;

import com.pustaka.pustaka.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    List<CommentDto> findCommentById(long postId);
    CommentDto findByCommentId(long postId, long commentId);
    CommentDto updateComment(long postId, long commentId, CommentDto request);
    void deleteComment(long postId, long commentId);
}
