package com.example.service.impl;


import com.example.converter.ClassesConverter;
import com.example.converter.CourseConverter;
import com.example.dto.CourseDTO;
import com.example.dto.CourseDTO;
import com.example.entity.ClassesEntity;
import com.example.entity.CourseEntity;
import com.example.repository.IClassesRepository;
import com.example.repository.ICourseRepository;
import com.example.service.ICourseService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseService implements ICourseService {

    private ICourseRepository iCourseRepository;
    private CourseConverter courseConverter;

    public CourseService(ICourseRepository iCourseRepository) {
        this.iCourseRepository = iCourseRepository;
        courseConverter = new CourseConverter();
    }

    @Override
    public List<CourseDTO> findAll() {
        List<CourseDTO> arr = new ArrayList<>();
        try {
            List<CourseEntity> entities = iCourseRepository.findAll();
            for (int i = 0; i < entities.size(); i++) {
                arr.add(courseConverter.convertToDto(entities.get(i)));
            }
        } catch (Exception e) {
            return arr;
        }
        return arr;
    }

    @Override
    public CourseDTO save(CourseDTO classesDTO) {
        CourseEntity courseEntity = courseConverter.convertToEntity(classesDTO);
        courseEntity.setModifiedDate(Timestamp.from(new Date().toInstant()));
        courseEntity.setCreatedDate(Timestamp.from(new Date().toInstant()));
        CourseEntity classesEntity = iCourseRepository.save(courseEntity);
        if (classesEntity != null) {
            return courseConverter.convertToDto(classesEntity);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
       try {
           iCourseRepository.delete(id);
       }catch (Exception e){
           return false;
       }
        return true;
    }
}
