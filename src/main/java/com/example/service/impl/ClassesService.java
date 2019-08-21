package com.example.service.impl;


import com.example.converter.ClassesConverter;
import com.example.dto.ClassesDTO;
import com.example.entity.ClassesEntity;
import com.example.repository.IClassesRepository;
import com.example.service.IClassesService;

import java.util.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ClassesService implements IClassesService {

    private IClassesRepository iClassesRepository;
    private ClassesConverter classesConverter;

    public ClassesService(IClassesRepository iClassesRepository) {
        this.iClassesRepository = iClassesRepository;
        classesConverter = new ClassesConverter();
    }

    @Override
    public List<ClassesDTO> findAll() {
        List<ClassesDTO> arr = new ArrayList<>();
        try {
            List<ClassesEntity> entities = iClassesRepository.findAll();
            for (int i = 0; i < entities.size(); i++) {
                arr.add(classesConverter.convertToDto(entities.get(i)));
            }
        } catch (Exception e) {
            return arr;
        }
        return arr;
    }

    @Override
    public ClassesDTO save(ClassesDTO classesDTO) {
        ClassesEntity ClassesModel = classesConverter.convertToEntity(classesDTO);
        ClassesModel.setModifiedDate(Timestamp.from(new Date().toInstant()));
        ClassesModel.setCreatedDate(Timestamp.from(new Date().toInstant()));
        ClassesEntity ClassesInserted = iClassesRepository.save(ClassesModel);
        if (ClassesInserted != null) {
            return classesConverter.convertToDto(ClassesInserted);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
       try {
           iClassesRepository.delete(id);
       }catch (Exception e){
           return false;
       }
        return true;
    }
}
