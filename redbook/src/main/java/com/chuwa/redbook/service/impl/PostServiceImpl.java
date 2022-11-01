package com.chuwa.redbook.service.impl;

import com.chuwa.redbook.dao.PostsRepository;
import com.chuwa.redbook.entity.Post;
import com.chuwa.redbook.exception.ResourceNotFoundException;
import com.chuwa.redbook.payload.PostDto;
import com.chuwa.redbook.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {


    @Autowired
    private PostsRepository postsRepository;

    @Override
    public PostDto createPost(PostDto postDto) {
//        Post post = new Post();
//        if (postDto.getTitle() != null) {
//            post.setTitle(postDto.getTitle());
//        } else {
//            post.setTitle("");
//        }
//
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
//
//        Post savedPost = postsRepository.save(post);
//        PostDto postResponse = new PostDto();
//        postResponse.setId(savedPost.getId());
//        postResponse.setTitle(savedPost.getTitle());
//        postResponse.setDescription(savedPost.getDescription());
//        postResponse.setContent(savedPost.getContent());

        Post post = mapToEntity(postDto);
        Post savedPost = postsRepository.save(post);
        PostDto postResponse = mapToDTO(savedPost);

        return postResponse;

    }

    @Override
    public List<PostDto> getAllPost() {
        List<Post> posts = postsRepository.findAll();
        List<PostDto> postsDtos = posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
        return postsDtos;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        return mapToDTO(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatePost = postsRepository.save(post);
        return mapToDTO(updatePost);
    }

    @Override
    public void deletePostById(long id) {
        Post post = postsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postsRepository.delete(post);
    }

    private PostDto mapToDTO(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());

        return postDto;
    }

    private Post mapToEntity(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        return post;
    }
}
