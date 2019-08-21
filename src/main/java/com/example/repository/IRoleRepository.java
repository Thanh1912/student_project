package com.example.repository;

import com.example.entity.RoleEntity;
import com.example.entity.UserEntity;


public interface IRoleRepository extends GenericRepository<Long, RoleEntity> {
    Boolean addRole(UserEntity userModel);
}
