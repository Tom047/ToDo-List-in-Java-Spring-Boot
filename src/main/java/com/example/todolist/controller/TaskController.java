package com.example.todolist.controller;

import com.example.todolist.dto.TaskDTO;
import com.example.todolist.entity.Task;
import com.example.todolist.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> create(@RequestBody TaskDTO dto) {
        return new ResponseEntity<>(taskService.create(dto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Task>> readAll() {
        return new ResponseEntity<>(taskService.readAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> readById(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.readById(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Task> update(@RequestBody Task task) {
        return new ResponseEntity<>(taskService.update(task), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        taskService.delete(id);
        return HttpStatus.OK;
    }
}
