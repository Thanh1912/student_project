package com.example.repository.impl;

import com.example.entity.RoleEntity;
import com.example.entity.UserEntity;
import com.example.repository.IRoleRepository;
import com.example.utils.DateConverter;


public class RoleRepository  extends AbstractRepository<Long, RoleEntity> implements IRoleRepository {

    public RoleRepository() {
    }

    @Override
    public Boolean addRole(UserEntity userModel) {
        userModel.setCreatedDate(DateConverter.getCurrentTimeStamp());
        userModel.setModifiedDate(DateConverter.getCurrentTimeStamp());
        RoleEntity roleEntity = new RoleEntity();
        return null;
    }

/*
    @Override
    public Boolean addRole(UserModel userModel) {
        String sql = "INSERT INTO `user_role`\n" +
                "(" +
                "`userid`," +
                "`roleid`," +
                "`createddate`," +
                "`modifieddate`)" +
                "VALUES(?,?,?,?)";
        List<Object> params = new ArrayList<>();
        Long idRole = userModel.getRoles().get(0).getId();
        params.add(userModel.getId());
        params.add(idRole);
        params.add(DateConverter.getCurrentTimeStamp());
        params.add(userModel.getModifiedDate());
        boolean flag = false;
        try {
            flag = jdbcUtils.updateByPreparedStatement(sql, params);
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }*/
}
