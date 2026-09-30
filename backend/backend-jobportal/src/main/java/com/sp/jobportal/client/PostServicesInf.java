package com.sp.jobportal.client;

import com.sp.jobportal.dto.PostDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.*;

import java.util.List;

@HttpExchange
public interface PostServicesInf {

    @GetExchange
    public List<PostDto> getAll();

    @GetExchange("/{id}")
    public PostDto getById(@PathVariable long id);

    @PostExchange
    public PostDto post(@RequestBody PostDto postDto);

    @PutExchange("/{id}")
    public PostDto put(@PathVariable long id, @RequestBody PostDto postDto);

    @DeleteExchange("/{id}")
    public void delete(@PathVariable long id);
}
