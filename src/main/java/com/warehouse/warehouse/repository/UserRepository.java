package com.warehouse.warehouse.repository;

import com.warehouse.warehouse.repository.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    public User findUserByLogin(String name);
}
