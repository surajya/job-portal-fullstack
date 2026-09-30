package com.sp.jobportal.client;

import com.sp.jobportal.dto.TodoDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.*;

import java.util.List;

@HttpExchange
public interface TodoServicesInf {

    //write abstract method for findAll
    @GetExchange
    public List<TodoDto> findAll();

    //write abstract method for findById
    @GetExchange("/{id}")
    public TodoDto findById(@PathVariable long id);

    @PostExchange
    public  TodoDto create(@RequestBody TodoDto todoDto);

    @PutExchange("/{id}")
    public  TodoDto update(@PathVariable long id ,@RequestBody TodoDto todoDto);

    @DeleteExchange("/{id}")
    public  void delete(@PathVariable long id);

}
