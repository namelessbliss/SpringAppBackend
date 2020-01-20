package com.nb.backend.dao;

import com.nb.backend.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IUserDao extends CrudRepository<User, Long> {

    public User findByEmail(String email);

    public User findByEmailAndPassword(String email, String password);

    @Query("select u from Users u where u.id=?1")
    public User findByIdSQL(Long id);

}
