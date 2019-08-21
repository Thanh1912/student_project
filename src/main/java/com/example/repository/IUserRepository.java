package com.example.repository;

import com.example.dto.SearchUser;
import com.example.entity.UserEntity;
import com.example.paging.PageRequest;
import com.example.paging.SearchResult;

public interface IUserRepository extends GenericRepository<Long, UserEntity> {
    UserEntity findByUserNameAndPassword(String username, String passwordOld);

    UserEntity updateSetPasswordByUserName(UserEntity user);

    SearchResult<UserEntity> search(SearchUser query, PageRequest pageRequest);
}
