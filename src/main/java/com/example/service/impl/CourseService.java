package com.example.service.impl;


import com.example.converter.ClassesConverter;
import com.example.converter.CourseConverter;
import com.example.dto.CourseDTO;
import com.example.dto.CourseDTO;
import com.example.dto.SearchCourse;
import com.example.dto.CourseDTO;
import com.example.entity.ClassesEntity;
import com.example.entity.CourseEntity;
import com.example.entity.CourseEntity;
import com.example.entity.UserCourseEntity;
import com.example.paging.PageRequest;
import com.example.paging.SearchResult;
import com.example.repository.IClassesRepository;
import com.example.repository.ICourseRepository;
import com.example.service.ICourseService;
import com.example.utils.StringUtils;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

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
    public SearchResult<CourseDTO> search(SearchCourse query, PageRequest pageRequest) {
        SearchResult<CourseDTO> res = new SearchResult<>();
        SearchResult<List<Object>> resultSearch = iCourseRepository.search(query, pageRequest);
/*        res.setItemTotal(resultSearch.getItemTotal());
        res.setLastPage(resultSearch.isLastPage());*/
        List<List<Object>> productModels = resultSearch.getResults();
        List<CourseDTO> courseDTOs = new ArrayList<>();
            CourseDTO userDto= new CourseDTO();
            List<Object> objects = productModels.get(0);
        for (int i = 0; i < objects.size(); i++) {
            Object[] objects2 = (Object[]) objects.get(i);
            try{
                userDto.setId(new Long(objects2[0]+""));
                userDto.setCode((String) objects2[1]);
                userDto.setRoom((String) objects2[2]);
                userDto.setName((String) objects2[3]);
                userDto.setCreatedDate(StringUtils.checkNullTimestamp(objects2[4]));
                userDto.setModifiedDate(StringUtils.checkNullTimestamp(objects2[5]));
                courseDTOs.add(userDto);
            }catch (Exception e){
            }
        }

        res.setResults(courseDTOs);
        return res;
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
    public UserCourseEntity findByUserIdAndCourseId(Long userId, Long courseId) {
        UserCourseEntity userCourseEntity = iCourseRepository.findByUserId(userId, courseId);
        return userCourseEntity;
    }

    @Override
    public boolean delete(Long id) {
        try {
            iCourseRepository.delete(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
