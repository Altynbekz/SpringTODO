package spring.code.project.todo.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequestDto {

    @JsonProperty(value = "task_name")
    @NotBlank(message = "Task name is required")
    @Size(min = 3, max = 100, message = "Task name must be between 3 and 100 characters")
    private String title;

    @JsonProperty(value = "task_description")
    @NotBlank
    @Size(max = 1000, message = "Task description must not exceed 1000 characters")
    private String taskDescription;


    @JsonProperty(value = "due_date")
    @FutureOrPresent(message = "Due date must be today or in the future")
    @NotNull
    private LocalDate dueDate;



}
