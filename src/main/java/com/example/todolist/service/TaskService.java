package com.example.todolist.service;

import com.example.todolist.dto.TaskDTO;
import com.example.todolist.entity.Task;
import com.example.todolist.entity.User;
import com.example.todolist.repository.TaskRepository;
import com.example.todolist.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public Task create(TaskDTO dto) {
        String currentUsername = userService.getCurrentUsername();
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + currentUsername));

        return taskRepository.save(Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(dto.isStatus())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .user(user)
                .build());
    }

    public List<Task> readAll() {
        String currentUsername = userService.getCurrentUsername();
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + currentUsername));

        return taskRepository.findAllByUser(user);
    }

    public Task readById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));
        validateTaskOwnership(task);
        return task;
    }

    public Task update(TaskDTO taskDTO, Long taskId) {
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));
        validateTaskOwnership(existingTask);

        existingTask.setTitle(taskDTO.getTitle());
        existingTask.setDescription(taskDTO.getDescription());
        existingTask.setStatus(taskDTO.isStatus());
        existingTask.setUpdatedAt(LocalDateTime.now());

        return taskRepository.save(existingTask);
    }

    public void delete(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));
        validateTaskOwnership(task);
        taskRepository.deleteById(id);
    }

    private void validateTaskOwnership(Task task) {
        String currentUsername = userService.getCurrentUsername();
        if (!task.getUser().getUsername().equals(currentUsername)) {
            throw new AccessDeniedException("You do not have permission to access this task");
        }
    }
}
