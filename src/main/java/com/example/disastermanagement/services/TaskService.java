package com.example.disastermanagement.services;

import com.example.disastermanagement.dao.TaskDAO;
import com.example.disastermanagement.models.Task;
import com.example.disastermanagement.models.TaskStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskService {

    private final TaskDAO taskDAO;

    @Autowired
    public TaskService(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    public void createTask(Task task) {
        taskDAO.save(task);
    }

    public Task getTaskById(Long id) {
        return taskDAO.findById(id);
    }

    public List<Task> getAllTasks() {
        return taskDAO.findAll();
    }

    public void updateTask(Task task) {
        taskDAO.update(task);
    }
    
    public boolean areAllTasksCompletedForReliefRequest(Long reliefRequestId) {
        List<Task> tasks = taskDAO.findByReliefRequestId(reliefRequestId);
        return tasks.stream().allMatch(task -> task.getStatus() == TaskStatus.COMPLETED);
    }
}