package com.example.service.impl;


import com.example.entity.UserCourseEntity;
import com.example.repository.IUserCourseRepository;
import com.example.service.IUserCourseService;

import java.util.List;

public class UserCourseService implements IUserCourseService {

    private IUserCourseRepository userCourseRepository;

    public UserCourseService(IUserCourseRepository userCourseRepository) {
        this.userCourseRepository = userCourseRepository;
    }


    @Override
    public List<UserCourseEntity> findByClassIdAndCourseId(Long classId, Long courseId) {
        List<UserCourseEntity> byClassIdAndCourseId = userCourseRepository.findByClassIdAndCourseId(classId, courseId);
        return byClassIdAndCourseId;

    }
}
