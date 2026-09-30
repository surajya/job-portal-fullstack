package com.sp.jobportal.client.controller;

import com.sp.jobportal.client.TodoServicesInf;
import com.sp.jobportal.client.services.RestClientTodoService;
import com.sp.jobportal.dto.TodoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodosController {

    private final RestClientTodoService restClientTodoService;

    private final TodoServicesInf todoServicesInf;

    @GetMapping
    public ResponseEntity<List<TodoDto>> getAllTodos() {

        //return ResponseEntity.ok(restClientTodoService.findAll());
        return ResponseEntity.ok(todoServicesInf.findAll());
    }

    //write code for find by id
    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> getTodoById(@PathVariable long id) {
        //return ResponseEntity.ok(restClientTodoService.findById(id));
        return ResponseEntity.ok(todoServicesInf.findById(id));
    }

    //write code for createdto
    @PostMapping
    public ResponseEntity<TodoDto> createTodo(@RequestBody TodoDto todoDto) {
        //return ResponseEntity.status(HttpStatus.CREATED).body(restClientTodoService.create(todoDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(todoServicesInf.create(todoDto));
    }

    //write code for update todoDto
    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> updateTodo(@RequestBody TodoDto todoDto, @PathVariable long id) {
        //return ResponseEntity.status(HttpStatus.OK).body(restClientTodoService.update(id, todoDto));
        return ResponseEntity.status(HttpStatus.OK).body(todoServicesInf.update(id, todoDto));
    }

    //write code for delete one tododto
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable long id) {
        //restClientTodoService.delete(id);
        todoServicesInf.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully.....");
    }
}
