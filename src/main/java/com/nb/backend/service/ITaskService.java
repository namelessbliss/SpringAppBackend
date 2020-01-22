package com.nb.backend.service;

import com.nb.backend.entity.Task;

import java.util.List;

public interface ITaskService {

    public List<Task> findAll();

    public void saveTask(Task task);

    public List<Task> getTaskUser(Long id);

    public Task findByIdSQL(Long id);

    public void deleteTask(Long id);
}
