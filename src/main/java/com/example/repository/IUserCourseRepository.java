package com.example.repository;


import com.example.entity.UserCourseEntity;

import java.util.List;

public interface IUserCourseRepository extends GenericRepository<Long, UserCourseEntity> {
    List<UserCourseEntity> findByClassIdAndCourseId(Long classId, Long courseId);
}
