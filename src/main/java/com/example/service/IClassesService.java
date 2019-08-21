package com.example.service;

import com.example.dto.ClassesDTO;
import com.example.dto.UserDTO;

import java.util.List;

public interface IClassesService {
    List<ClassesDTO> findAll();
    ClassesDTO save(ClassesDTO categoryDTO);
    boolean delete(Long id);
}
