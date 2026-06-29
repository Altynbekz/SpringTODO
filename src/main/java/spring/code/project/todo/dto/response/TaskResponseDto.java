package spring.code.project.todo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskResponseDto {
    private Long id;
    private String title;
    private String taskDescription;
    private LocalDate createdAt;
    private LocalDate dueDate;
    private boolean status;
}
