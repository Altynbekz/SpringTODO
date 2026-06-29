package spring.code.project.todo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.code.project.todo.dto.request.TaskRequestDto;
import spring.code.project.todo.dto.response.TaskResponseDto;
import spring.code.project.todo.service.TaskServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskServiceImpl taskService;

    @GetMapping("/test")
    public TaskRequestDto getTest() {
        return new TaskRequestDto(
                "Sample Task Title",
                "Sample Task Description",
                java.time.LocalDate.now().plusDays(50));
    }

    @GetMapping("/{id}")
    public TaskResponseDto getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @GetMapping
    public List<TaskResponseDto> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping()
    public TaskResponseDto createTask(@RequestBody TaskRequestDto taskRequestDto) {
        return taskService.createTask(taskRequestDto);
    }

    @PutMapping("/{id}")
    public TaskResponseDto updateTask(@PathVariable Long id, @RequestBody TaskRequestDto taskRequestDto) {
        return taskService.updateTask(id, taskRequestDto);
    }

    @PatchMapping("/{id}")
    public TaskResponseDto partialUpdateTask(@PathVariable Long id,
                                             @RequestBody java.util.Map<String, Object> updates) {
        return taskService.partialUpdateTask(id, updates);
    }
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }




}
