package com.example.service;

import com.example.dto.CourseDTO;
import com.example.dto.SearchCourse;
import com.example.entity.UserCourseEntity;
import com.example.paging.PageRequest;
import com.example.paging.SearchResult;

import java.util.List;

public interface ICourseService {
    List<CourseDTO> findAll();
    CourseDTO save(CourseDTO categoryDTO);
    UserCourseEntity findByUserIdAndCourseId(Long userId, Long courseId);
    SearchResult<CourseDTO> search(SearchCourse query, PageRequest pageRequest);
    boolean delete(Long id);
}
