package com.nb.backend.service;

import com.nb.backend.dao.ITaskDao;
import com.nb.backend.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskServiceImpl implements ITaskService {

    @Autowired
    private ITaskDao taskDao;

    @Override
    @Transactional(readOnly = true)
    public List<Task> findAll() {
        return (List<Task>) taskDao.findAll();
    }

    @Override
    @Transactional
    public void saveTask(Task task) {
        taskDao.save(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getTaskUser(Long id) {
        return (List<Task>) taskDao.findByUserId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Task findByIdSQL(Long id) {
        return taskDao.findByIdSQL(id);
    }

    @Override
    public void deleteTask(Long id) {
        taskDao.deleteById(id);
    }
}
