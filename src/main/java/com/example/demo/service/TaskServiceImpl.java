package com.example.demo.service;

import com.example.demo.dao.tasks.TaskRepository;
import com.example.demo.entities.Tasks;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Service
@Transactional
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;

    TaskServiceImpl(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public List<Tasks> getAllTodos(Date date) {
        return taskRepository.findByTaskDate(date);
    }

    public Tasks save(Tasks task){
        try {
            Tasks savedTask = taskRepository.save(task);

            if (savedTask != null && savedTask.getId() != null) {
                System.out.println("Task saved successfully with ID: " + savedTask.getId());
            } else {
                System.out.println("Task saving returned null");
            }

            return savedTask;

        } catch (DataAccessException ex) {
            System.err.println("Database error while saving task: " + ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            System.err.println("Unexpected error while saving task: " + ex.getMessage());
            throw ex;
        }
    }

    @Override
    public Tasks updateTasks(Tasks task) {
        Tasks existingTask = taskRepository.findByTaskDateAndTaskTime(task.getTaskDate(), task.getTaskTime());
        if (existingTask != null) {
            existingTask.setTask(task.getTask());
            existingTask.setEdited(task.getEdited());
            taskRepository.save(existingTask);
            return existingTask;
        }
        return null;
    }

    @Override
    public void deleteByTaskDateAndTaskTime(Date taskDate, Time taskTime) {
        taskRepository.deleteByTaskDateAndTaskTime(taskDate,taskTime);
    }
}
