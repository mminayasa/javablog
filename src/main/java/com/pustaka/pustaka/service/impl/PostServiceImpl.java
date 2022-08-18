package com.pustaka.pustaka.service.impl;

import com.pustaka.pustaka.exception.ResourceNotFound;
import com.pustaka.pustaka.model.Post;
import com.pustaka.pustaka.payload.PostDto;
import com.pustaka.pustaka.payload.PostResponse;
import com.pustaka.pustaka.repository.PostRepository;
import com.pustaka.pustaka.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository,
                           ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public PostDto createPost(PostDto postDto) {
        // Convert DTO to entity
        Post post = mapToEntity(postDto);
        Post newPost = postRepository.save(post);

        // Convert entity to Dto
        PostDto response = mapToDto(newPost);
        return  response;
    }

    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
        // check condition sort asc desc
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        // create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> postList = posts.getContent();
        List<PostDto> contents = postList.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(contents);
        postResponse.setPageNo(pageNo);
        postResponse.setPageSize(pageSize);
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        return postResponse;
//        return posts.map(this::mapToDto);
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Post", "id", String.valueOf(id)));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        // get post by id
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Post", "id", String.valueOf(id)));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        postDto.setContent(postDto.getContent());
        Post updatePost = postRepository.save(post);
        return mapToDto(updatePost);
    }

    @Override
    @Transactional
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Post", "id", String.valueOf(id)));
        postRepository.delete(post);
    }

    private PostDto mapToDto(Post post) {
        PostDto postDto = modelMapper.map(post, PostDto.class);
//        PostDto postDto = new PostDto();
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());
        return postDto;
    }

    private Post mapToEntity(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }
}
