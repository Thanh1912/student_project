package com.example.repository.impl;

import com.example.dto.SearchUser;
import com.example.entity.UserEntity;
import com.example.paging.PageRequest;
import com.example.paging.SearchResult;
import com.example.repository.IUserRepository;
import com.example.utils.HibernateUtil;
import com.example.utils.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository extends AbstractRepository<Long, UserEntity> implements IUserRepository {

    @Override
    public UserEntity findByUserNameAndPassword(String username, String passwordOld) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("password", passwordOld);
        return this.findOneByProperty(properties);
    }

    @Override
    public UserEntity updateSetPasswordByUserName(UserEntity user) {
        return null;
    }

    @Override
    public SearchResult<UserEntity> search(SearchUser query, PageRequest pageRequest) {
        SearchResult<UserEntity> res = new SearchResult<>();
        StringBuilder sql = new StringBuilder("from UserEntity");
        sql.append(" where 1=1 ");
        if (!StringUtils.isEmpty(query.getFullname())) {
            sql.append(" and fullname LIKE '%" + query.getFullname() + "%'");
        }
        if (query.getClassId() != null && query.getClassId() >= 0) {
            sql.append(" and classId = " + query.getClassId() + " ");
        }
//        sql.append(" limit " + (pageRequest.getPage() - 1) * pageRequest.getLimit() + ", " + pageRequest.getLimit());

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<UserEntity> result = new ArrayList<>();
        try {
            System.out.println("Search query : " + sql.toString());
            Query bquery = session.createQuery(sql.toString());
            bquery.setFirstResult((pageRequest.getPage() - 1) * pageRequest.getLimit());
            bquery.setMaxResults(pageRequest.getLimit());
            result =  (List<UserEntity>)  bquery.list();
            transaction.commit();
            res.setResults(result);
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            session.close();
        }
        return res;
    }
}
