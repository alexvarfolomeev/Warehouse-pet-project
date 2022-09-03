package com.warehouse.warehouse.repository;

import com.warehouse.warehouse.repository.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role getRoleById(Long id);
}
