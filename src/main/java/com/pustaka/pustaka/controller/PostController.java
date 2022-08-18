package com.pustaka.pustaka.controller;

import com.pustaka.pustaka.payload.PostDto;
import com.pustaka.pustaka.payload.PostResponse;
import com.pustaka.pustaka.service.PostService;
import com.pustaka.pustaka.utils.AppConstant;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto request) {
        return new ResponseEntity<>(postService.createPost(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PostResponse> getAllPost(@RequestParam(value = "pageNo", defaultValue = AppConstant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                   @RequestParam(value = "pageSize", defaultValue = AppConstant.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                   @RequestParam(value = "sortBy", defaultValue = AppConstant.DEFAULT_SORT_BY, required = false) String sortBy,
                                                   @RequestParam(value = "sortDir", defaultValue = AppConstant.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return new ResponseEntity<>(postService.getAllPost(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> findPostById(@PathVariable(name = "id") long id) {
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable(name = "id") long id, @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.updatePost(postDto, id), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable(name = "id") long id) {
        postService.deletePostById(id);
        return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
    }
}
