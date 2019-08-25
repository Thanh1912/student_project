package com.example.service.impl;


import com.example.converter.RoleConverter;
import com.example.converter.UserConverter;
import com.example.dto.RoleDTO;
import com.example.dto.SearchUser;
import com.example.dto.UserCourseDTO;
import com.example.dto.UserDTO;
import com.example.entity.UserEntity;
import com.example.entity.RoleEntity;
import com.example.paging.PageRequest;
import com.example.paging.SearchResult;
import com.example.repository.IRoleRepository;
import com.example.repository.IUserRepository;
import com.example.repository.impl.RoleRepository;
import com.example.repository.impl.UserRepository;
import com.example.service.IUserService;
import com.example.utils.StringUtils;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

public class UserService implements IUserService {

    private IUserRepository userRepository;
    private IRoleRepository roleRepository;
    private UserConverter userConverter;
    private RoleConverter roleConverter;

    public UserService() {
        userRepository = new UserRepository();
        roleRepository = new RoleRepository();
        userConverter = new UserConverter();
        roleConverter = new RoleConverter();
    }

    @Override
    public List<UserDTO> findAll() {
        List<UserDTO> arr = new ArrayList<>();
        try {
            List<UserEntity> entities = userRepository.findAll();
            for (int i = 0; i < entities.size(); i++) {
                arr.add(userConverter.convertToDto(entities.get(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    @Override
    public UserDTO findOneByUsernameAndPassword(String username, String password) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("password", password);
        UserEntity entities = userRepository.findOneByProperty(properties);
        if (entities == null) return null;
        UserDTO dto = userConverter.convertToDto(entities);
        List<RoleDTO> roleDTOList = new ArrayList<>();
        for (int i = 0; i < entities.getRoles().size(); i++) {
            RoleEntity roleModel = entities.getRoles().get(i);
            roleDTOList.add(roleConverter.convertToDto(roleModel));
        }
        dto.setRoles(roleDTOList);
        return dto;
    }

    @Override
    public UserDTO changePassword(String username, String passwordOld, String passwordNew) {
        if (userRepository.findByUserNameAndPassword(username, passwordOld) != null) { // xac thuc tai khoan
            UserEntity user = userRepository.findOneByProperty("username", username);
            user.setUsername(username);
            user.setPassword(passwordNew);
            UserEntity userModel = userRepository.update(user);
            if (userModel != null) {
                return userConverter.convertToDto(userModel);
            }
        }
        return null;
    }

    @Override
    public UserDTO insert(UserDTO userDTO) {
        UserEntity userModel = userConverter.convertToEntity(userDTO);
        userModel.setModifiedDate(Timestamp.from(new Date().toInstant()));
        userModel.setCreatedDate(Timestamp.from(new Date().toInstant()));
        try{
            UserEntity userInserted = userRepository.saveOrUpdate(userModel);
            if (userInserted != null) {
                return userConverter.convertToDto(userInserted);
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }

    @Override
    public SearchResult<UserDTO> search(SearchUser query, PageRequest pageRequest) {
        SearchResult<UserDTO> res = new SearchResult<>();
        SearchResult<UserEntity> resultSearch = userRepository.search(query, pageRequest);
        res.setItemTotal(resultSearch.getItemTotal());
        res.setLastPage(resultSearch.isLastPage());
        List<UserEntity> productModels = resultSearch.getResults();
        res.setResults(productModels.stream().map(item -> userConverter.convertToDto(item)).collect(Collectors.toList()));
        return res;
    }

    public Long getLong(Object o){
        return new Long(o.toString());
    }
    public SearchResult<UserCourseDTO> searchAll(SearchUser query, PageRequest pageRequest) {
        SearchResult<UserCourseDTO> res = new SearchResult<>();
        List<Object> resultSearch = userRepository.searchAll(query, pageRequest);
        List<UserCourseDTO> userCourseDTOS = new ArrayList<>();
        for (int i = 0; i < resultSearch.size(); i++) {
            Object[] objects = (Object[]) resultSearch.get(i);
            try{
                UserCourseDTO userCourseDTO =new UserCourseDTO();
                UserDTO userDTO = new UserDTO();
                userDTO.setId(new Long(objects[0].toString()));
                userDTO.setUsername((String)StringUtils.checkNull(objects[1]));
                userDTO.setFullname((String)StringUtils.checkNull(objects[2]));
                userDTO.setCardId((String)StringUtils.checkNull(objects[3]));
                userDTO.setMssv((String) StringUtils.checkNull(objects[4]));
                userDTO.setSex((String)objects[5]);
                userDTO.setClassId(new Long(objects[6].toString()));
                userCourseDTO.setUserDTO(userDTO);
                userCourseDTO.setCourseid(getLong(objects[7]));
                userCourseDTO.setUserid(getLong( objects[8]));
                userCourseDTO.setId(getLong(objects[9]));
                userCourseDTO.setPointHk((Double) objects[10]);
                userCourseDTO.setPointHkEnd((Double) objects[11]);
                userCourseDTO.setPointHkAnother((Double) objects[12]);
                userCourseDTO.setPointHkAnother((Double) objects[13]);
                userCourseDTO.setStatus((String) objects[14]);
                userCourseDTO.setStatusPoint((String) objects[15]);
                if(userDTO.getId()!=null)
                    userCourseDTOS.add(userCourseDTO);
            }catch (Exception e){

            }
        }

        res.setResults(userCourseDTOS);
        return res;
    }

}
