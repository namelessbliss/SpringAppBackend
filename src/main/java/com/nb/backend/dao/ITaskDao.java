package com.nb.backend.dao;

import com.nb.backend.entity.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ITaskDao extends CrudRepository<Task, Long> {

    public List<Task> findByUserId(Long id);

    @Query("select t from Task t where t.id=?1")
    public Task findByIdSQL(Long id);
}
