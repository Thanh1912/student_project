package com.example.service.impl;


import com.example.converter.RoleConverter;
import com.example.dto.RoleDTO;
import com.example.entity.RoleEntity;
import com.example.repository.IRoleRepository;
import com.example.repository.impl.RoleRepository;
import com.example.service.IRoleService;

import java.util.ArrayList;
import java.util.List;

public class RoleService implements IRoleService {

    private IRoleRepository roleRepository;
    private RoleConverter roleConverter;

    public RoleService() {
        roleRepository = new RoleRepository();
        roleConverter = new RoleConverter();
    }

    @Override
    public List<RoleDTO> findAll() {
        List<RoleDTO> arr = new ArrayList<>();
        try {
            List<RoleEntity> entities = roleRepository.findAll();
            for (int i = 0; i < entities.size(); i++) {
                arr.add(roleConverter.convertToDto(entities.get(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }
}
