package com.example.service.impl;


import com.example.converter.CourseConverter;
import com.example.dto.CourseDTO;
import com.example.dto.SearchCourse;
import com.example.dto.UserCourseDTO;
import com.example.dto.UserDTO;
import com.example.entity.CourseEntity;
import com.example.entity.UserCourseEntity;
import com.example.entity.UserEntity;
import com.example.paging.PageRequest;
import com.example.paging.SearchResult;
import com.example.repository.ICourseRepository;
import com.example.repository.IUserCourseRepository;
import com.example.service.ICourseService;
import com.example.service.IUserCourseService;
import com.example.utils.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserCourseService implements IUserCourseService {

    private IUserCourseRepository userCourseRepository;

    public UserCourseService(IUserCourseRepository userCourseRepository) {
        this.userCourseRepository = userCourseRepository;
    }


    @Override
    public List<UserCourseEntity> findByClassIdAndCourseId(Long classId, Long courseId) {

        List<UserCourseEntity> userCourseEntities = new ArrayList<>();
        List<Object> byClassIdAndCourseId = userCourseRepository.findByClassIdAndCourseId(classId, courseId);
        for (int i = 0; i < byClassIdAndCourseId.size(); i++) {
            Object[] objects = (Object[]) byClassIdAndCourseId.get(i);
            try {
                UserEntity userEntity =new UserEntity();
                CourseEntity courseEntity = new CourseEntity();
                courseEntity.setId(new Long(objects[0].toString()));
                courseEntity.setId(new Long(objects[0].toString()));
            } catch (Exception e) {

            }
        }
        return userCourseEntities;

    }
}
