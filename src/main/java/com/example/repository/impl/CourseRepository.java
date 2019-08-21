package com.example.repository.impl;

import com.example.entity.CourseEntity;
import com.example.entity.RoleEntity;
import com.example.entity.UserEntity;
import com.example.repository.ICourseRepository;
import com.example.repository.IRoleRepository;


public class CourseRepository extends AbstractRepository<Long, CourseEntity> implements ICourseRepository {

    public CourseRepository() {
    }
}
