package com.amandasantos.todolist.controller;

import com.amandasantos.todolist.entity.TaskEntity;
import com.amandasantos.todolist.repository.TaskRepository;
import com.amandasantos.todolist.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskEntity taskEntity, HttpServletRequest request) {

        var idUser = request.getAttribute("user_id");

        var user = this.userRepository.findById((Long) idUser).orElseThrow();

        taskEntity.setUser(user);

        var currentData = LocalDateTime.now();
        if (currentData.isAfter(taskEntity.getDueDate())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de início deve ser maior do que a data atual");
        }

        var taskCreated = this.taskRepository.save(taskEntity);
        return ResponseEntity.ok().body(taskCreated);
    }

    @GetMapping("/")
    public List<TaskEntity> list(HttpServletRequest request) {
        var idUser = request.getAttribute("user_id");

        return this.taskRepository.findByUserId((Long) idUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskEntity taskEntity,
                                 HttpServletRequest request,
                                 @PathVariable Long id) {

        var idUser = (Long) request.getAttribute("user_id");

        var task = this.taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task não encontrada"));

        if (!task.getUser().getId().equals(idUser)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuário não tem permissão para alterar essa task");
        }

        task.setTitle(taskEntity.getTitle());
        task.setDescription(taskEntity.getDescription());
        task.setPriority(taskEntity.getPriority());
        task.setDueDate(taskEntity.getDueDate());
        task.setCompleted(taskEntity.getCompleted());

        var updatedTask = this.taskRepository.save(task);

        System.out.println("ID USER LOGADO: " + idUser);
        System.out.println("ID USER DA TASK: " + task.getUser().getId());

        return ResponseEntity.ok(updatedTask);
    }
}
