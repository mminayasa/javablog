package com.pustaka.pustaka.controller;

import com.pustaka.pustaka.model.Comment;
import com.pustaka.pustaka.payload.CommentDto;
import com.pustaka.pustaka.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{id}/comment")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "id") long id,
                                                    @Valid @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(id, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{id}/comment")
    public ResponseEntity<List<CommentDto>> findCommentByPostId(@PathVariable(value = "id") long id) {
        return new ResponseEntity<>(commentService.findCommentById(id), HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}/comment/{commentId}")
    public ResponseEntity<CommentDto> findCommentByCommentId(@PathVariable(value = "postId") long postId,
                                                             @PathVariable(value = "commentId") long commentId) {
        return new ResponseEntity<>(commentService.findByCommentId(postId, commentId), HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comment/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postId") long postId,
                                                    @PathVariable(value = "commentId") long commentId,
                                                    @Valid @RequestBody CommentDto request) {
        return new ResponseEntity<>(commentService.updateComment(postId, commentId, request), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/posts/{postId}/comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") long postId,
                                                @PathVariable(value = "commentId") long commentId) {
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Successfully Deleted", HttpStatus.NO_CONTENT);
    }
}
