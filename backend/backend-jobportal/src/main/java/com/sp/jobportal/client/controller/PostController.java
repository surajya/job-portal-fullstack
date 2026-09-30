package com.sp.jobportal.client.controller;

import com.sp.jobportal.client.PostServicesInf;
import com.sp.jobportal.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {


    private final PostServicesInf postServicesInf;

    @GetMapping
    public ResponseEntity<List<PostDto>> getPosts() {
        return ResponseEntity.ok(postServicesInf.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable long id) {
        return ResponseEntity.ok(postServicesInf.getById(id));
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postServicesInf.post(postDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable long id ,@RequestBody PostDto postDto) {
        return ResponseEntity.ok(postServicesInf.put(id,postDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully.....");
    }
}
