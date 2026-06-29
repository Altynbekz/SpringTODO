package spring.code.project.todo.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import spring.code.project.todo.dto.request.TaskRequestDto;
import spring.code.project.todo.dto.response.TaskResponseDto;
import spring.code.project.todo.entity.TaskEntity;
import spring.code.project.todo.repository.TaskRepository;
import spring.code.project.todo.service.serviceInterfaces.ITaskService;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements ITaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    //ADdtional methods
    private boolean titleExists(String title){
        return !taskRepository.findByTitle(title).isEmpty();
    }

    private boolean existsById(Long id){
        return taskRepository.existsById(id);
    }

    @Override
    public TaskResponseDto getTaskById(Long id) {
        TaskEntity taskEntity = taskRepository.findById(id)
                // temporarily throwing a RuntimeException
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        return modelMapper.map(taskEntity, TaskResponseDto.class);
    }

    @Override
    public TaskResponseDto createTask(TaskRequestDto taskRequestDto) {
        if(taskRequestDto.getTitle() == null ||
                titleExists(taskRequestDto.getTitle()))
        {
            throw new IllegalArgumentException("Task title cannot be null or empty or already exists.");
        }
        TaskEntity entity = modelMapper.map(taskRequestDto, TaskEntity.class);
        taskRepository.save(entity);
        System.out.println(taskRequestDto.getDueDate());
        return modelMapper.map(entity, TaskResponseDto.class);
    }

    @Override
    public TaskResponseDto updateTask(Long id, TaskRequestDto taskRequestDto) {
        TaskEntity entity = taskRepository.
                findById(id).
                orElseThrow(() ->
                        new RuntimeException("Task not found with id: " + id));

        modelMapper.map(taskRequestDto, entity);
        taskRepository.save(entity);

        return modelMapper.map(entity, TaskResponseDto.class);
    }

    @Override
    public void deleteTask(Long id) {
        if(!existsById(id)){
            throw new RuntimeException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }

    @Override
    public TaskResponseDto partialUpdateTask(Long id, Map<String, Object> updates) {

        TaskEntity entity = taskRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        updates.forEach((key, value) -> {
            if(key.equals("id") || key.equals("createdAt")){
                throw new RuntimeException("Cannot update id or createdAt fields.");
            }
            Field field = ReflectionUtils.findField(TaskEntity.class, key);
            if(field == null){
                throw new RuntimeException("Field " + key + " does not exist in TaskEntity.");
            }
            field.setAccessible(true);
            ReflectionUtils.setField(field, entity, value);}
                );
        taskRepository.save(entity);
        return modelMapper.map(entity, TaskResponseDto.class);
    }

    @Override
    public List<TaskResponseDto> getTaskByTitle(String title) {

        if(!titleExists(title)){
            throw new RuntimeException("Task not found with title: " + title);
        }

        List<TaskEntity> taskEntities = taskRepository.findByTitle(title);
        return taskEntities.stream().map(entity
                -> modelMapper.map(entity, TaskResponseDto.class)).
                toList();
    }

    @Override
    public List<TaskResponseDto> getTaskByStatus(boolean status) {
        List<TaskEntity> entities = taskRepository.findByStatus(status);
        if(entities.isEmpty()){
            throw new RuntimeException("No tasks found with status: " + status);
        }

        return entities.stream().
                map(entity ->
                        modelMapper.map(entity, TaskResponseDto.class))
                .toList();
    }

    @Override
    public List<TaskResponseDto> getByDueDate(LocalDate dueDate) {
        List<TaskEntity> entities = taskRepository.findByDueDate(dueDate);
        if(entities.isEmpty())
            throw new RuntimeException("No tasks found with due date: " + dueDate);


        return entities.stream().
                map(entity -> modelMapper.map(entity, TaskResponseDto.class))
                        .toList();
    }

    @Override
    public List<TaskResponseDto> getByCreatedDate(LocalDate createdDate) {
        List<TaskEntity> entities = taskRepository.findByCreatedAt(createdDate);
        if(entities.isEmpty())
            throw new RuntimeException("No tasks found with created date: " + createdDate);
        return entities.stream().
                map(entity -> modelMapper.map(entity, TaskResponseDto.class)).
                toList();
    }

    @Override
    public List<TaskResponseDto> getAllTasks() {
        return taskRepository.findAll().stream().
                map(entity -> modelMapper.map(entity, TaskResponseDto.class)).
                toList();
    }


}
