package com.example.service;

import com.example.dto.SearchUser;
import com.example.dto.UserCourseDTO;
import com.example.dto.UserDTO;
import com.example.entity.UserCourseEntity;
import com.example.paging.PageRequest;
import com.example.paging.SearchResult;

import java.util.List;

public interface IUserCourseService {
    List<UserCourseEntity> findByClassIdAndCourseId(Long classId, Long courseId);
}
