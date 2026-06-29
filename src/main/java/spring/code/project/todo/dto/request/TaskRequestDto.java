package spring.code.project.todo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.internal.bytebuddy.utility.nullability.NeverNull;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(value = "task_id")
    private Long id;

    @JsonProperty(value = "task_name")
    @NotBlank(message = "Task name is required")
    private String taskName;

    @JsonProperty(value = "task_description")
    @NotBlank
    @NotNull(message = "Task description is required")
    private String taskDescription;

    @JsonProperty(value = "created_at")
    @Column(updatable = false)
    private LocalDate createdDate = LocalDate.now();

    @JsonProperty(value = "due_date")
    private LocalDate dueDate;

    @JsonProperty(value = "completed")
    private boolean completed = false;
}
