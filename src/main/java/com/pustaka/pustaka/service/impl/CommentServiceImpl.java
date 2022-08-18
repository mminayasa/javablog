package com.pustaka.pustaka.service.impl;

import com.pustaka.pustaka.exception.BlogAPIException;
import com.pustaka.pustaka.exception.ResourceNotFound;
import com.pustaka.pustaka.model.Comment;
import com.pustaka.pustaka.model.Post;
import com.pustaka.pustaka.payload.CommentDto;
import com.pustaka.pustaka.repository.CommentRepository;
import com.pustaka.pustaka.repository.PostRepository;
import com.pustaka.pustaka.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository,
                              PostRepository postRepository,
                              ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);

        // retrieve post entity by id
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFound("Post", "id", String.valueOf(postId)));

        // set post to comment entity
        comment.setPost(post);

        // comment entity to DB
        Comment newComment = commentRepository.save(comment);
        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> findCommentById(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto findByCommentId(long postId, long commentId) {
        Comment comment = findComment(postId, commentId);
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto request) {
        Comment comment = findComment(postId, commentId);
        comment.setName(request.getName());
        comment.setEmail(request.getEmail());
        comment.setBody(request.getBody());
        Comment updateComment = commentRepository.save(comment);
        return mapToDto(updateComment);
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Comment comment = findComment(postId, commentId);
        commentRepository.delete(comment);
    }

    private Comment findComment(long postId, long commentId) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFound("Post", "id", String.valueOf(postId)));
        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFound("Comment", "id", String.valueOf(commentId)));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment doesn't belong to post");
        }
        return comment;
    }

    private CommentDto mapToDto(Comment comment) {
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;
    }
}
