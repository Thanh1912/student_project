package com.example.service;

import com.example.dto.SearchUser;
import com.example.dto.UserCourseDTO;
import com.example.dto.UserDTO;
import com.example.paging.PageRequest;
import com.example.paging.SearchResult;

import java.util.List;

public interface IUserService {
    List<UserDTO> findAll();
    UserDTO findOneByUsernameAndPassword(String username,String password);
    UserDTO changePassword(String username,String passwordOld, String passwordNew);
    UserDTO insert(UserDTO userDTO);
    SearchResult<UserDTO> search(SearchUser query, PageRequest pageRequest);
    SearchResult<UserCourseDTO> searchAll(SearchUser query, PageRequest pageRequest);
}
