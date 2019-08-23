package com.example.repository;

import com.example.dto.SearchUser;
import com.example.entity.UserEntity;
import com.example.paging.PageRequest;
import com.example.paging.SearchResult;

import java.util.List;

public interface IUserRepository extends GenericRepository<Long, UserEntity> {
    UserEntity findByUserNameAndPassword(String username, String passwordOld);

    UserEntity updateSetPasswordByUserName(UserEntity user);

    List<Object> searchAll(SearchUser query, PageRequest pageRequest);

    SearchResult<UserEntity> search(SearchUser query, PageRequest pageRequest);
}
