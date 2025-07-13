package com.example.demo.service;

import com.example.demo.entities.Tasks;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface TaskService{
    public List<Tasks> getAllTodos(Date date);

    public Tasks save(Tasks task);

    public Tasks updateTasks(Tasks task);

    void deleteByTaskDateAndTaskTime(Date taskDate, Time taskTime);
}
