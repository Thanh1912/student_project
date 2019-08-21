package com.example.service;

import com.example.dto.CourseDTO;

import java.util.List;

public interface ICourseService {
    List<CourseDTO> findAll();
    CourseDTO save(CourseDTO categoryDTO);
    boolean delete(Long id);
}
