package com.example.demo.controller;

import com.example.demo.entities.Tasks;
import com.example.demo.service.TaskService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/todo-app/v1")
public class GenericController {

    @Autowired
    TaskService taskService;

    @GetMapping("/get-tasks")
    public ResponseEntity<List<Tasks>> getTasksForUser(@RequestParam("date") String date ){


        List<Tasks> tasks = taskService.getAllTodos(Date.valueOf(date));
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("save-task")
    public ResponseEntity<Map<String, String> > saveTasks(@RequestBody Tasks task){
        Map<String, String> response = new HashMap<>();
        try {
            Tasks savedTask = taskService.save(task);
            if(savedTask !=null && savedTask.getId() != null) {
                response.put("message", "success");
                return ResponseEntity.ok(response);
            }
            response.put("message", "failure");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception ex) {
            System.err.println("Error saving task: " + ex.getMessage());
            response.put("message", "Failed to save task try again later:" + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }

    @PostMapping("update-task")
    public ResponseEntity<Map<String, String>> updateTask(@RequestBody Tasks task) {
        System.out.println(task);

        Map<String, String> response = new HashMap<>();
        try {
            Tasks updatedTasks = taskService.updateTasks(task);
            if(updatedTasks != null){
                response.put("message", "updated");
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "task not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("message", "error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }



    @PostMapping("delete-task")
    public ResponseEntity<Map<String, String>> deleteTask(@RequestBody Tasks task) {
        System.out.println(task);
        Map<String, String> response = new HashMap<>();
        try {
            taskService.deleteByTaskDateAndTaskTime(task.getTaskDate(),task.getTaskTime());
            System.out.println("deleted");
            response.put("message", "deleted");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
