package com.example.repository;


import com.example.dto.SearchCourse;
import com.example.entity.CourseEntity;
import com.example.entity.UserCourseEntity;
import com.example.paging.PageRequest;
import com.example.paging.SearchResult;

import java.util.List;

public interface ICourseRepository extends GenericRepository<Long, CourseEntity> {
    SearchResult<List<Object>> search(SearchCourse query, PageRequest pageRequest);
    UserCourseEntity findByUserId(Long userId, Long courseId);
}
