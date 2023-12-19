package com.example.todolist.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDTO {

    private Long id;
    private String title;
    private String description;
    private boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;
}
