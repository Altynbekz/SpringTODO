package spring.code.project.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.code.project.todo.entity.TaskEntity;

import java.time.LocalDate;
import java.util.List;


public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findByTitle(String title);
    List<TaskEntity> findByStatus(boolean status);
    List<TaskEntity> findByDueDate(LocalDate dueDate);
    List<TaskEntity> findByCreatedAt(LocalDate createdAt);

}
