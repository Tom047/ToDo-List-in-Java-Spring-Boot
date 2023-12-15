package com.example.todolist.service;

import com.example.todolist.dto.TaskDTO;
import com.example.todolist.entity.Task;
import com.example.todolist.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public Task create(TaskDTO dto) {
        return taskRepository.save(Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(dto.isStatus())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build());
    }

    public List<Task> readAll() {
        return taskRepository.findAll();
    }

//    public List<Task> readById(Long id) {
//        return taskRepository.findById(id);
//    }

    public Task update(Task product) {
        return taskRepository.save(product);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
