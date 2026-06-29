package spring.code.project.todo.service.serviceInterfaces;

import spring.code.project.todo.dto.request.TaskRequestDto;
import spring.code.project.todo.dto.response.TaskResponseDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ITaskService {

    TaskResponseDto getTaskById(Long id);

    TaskResponseDto createTask(TaskRequestDto taskRequestDto);

    TaskResponseDto updateTask(Long id, TaskRequestDto taskRequestDto);

    void deleteTask(Long id);

    TaskResponseDto partialUpdateTask(Long id, Map<String, Object> updates);

    //query methods
    List<TaskResponseDto> getTaskByTitle(String title);

    List<TaskResponseDto> getTaskByStatus(boolean status);

    List<TaskResponseDto> getByDueDate(LocalDate dueDate);

    List<TaskResponseDto> getByCreatedDate(LocalDate createdDate);

    List<TaskResponseDto> getAllTasks();




}
