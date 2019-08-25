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


    public List<Object> searchAll(SearchUser query, PageRequest pageRequest) {
        StringBuilder sql = new StringBuilder("select user.id, user.username, user.fullname, user.cardId,  user.mssv, user.sex, user.classId");

        sql.append(",user_course.courseid, user_course.userid, user_course.user_course_id, user_course.point_hk1, user_course.point_hk_end,user_course.point_hk_another,user_course.point, user_course.status as course_status,  user_course.status_point from user_course as user_course");
        sql.append(" INNER JOIN user as user on user_course.userid = user.id ");
        sql.append(" INNER JOIN course as course on course.id = user_course.courseid ");
        sql.append(" where 1=1 ");
        if (!StringUtils.isEmpty(query.getFullname())) {
            sql.append(" and fullname LIKE '%" + query.getFullname() + "%'");
        }
        if (query.getClassId() != null && query.getClassId() >= 0) {
            sql.append(" and classId ='" + query.getClassId() + "'");
        }
        if (query.getCodeCourse() != null && query.getCodeCourse().length() >= 0) {
            sql.append(" and course.code ='" + query.getCodeCourse() + "'");
        }

        if (query.getUserId() != null && query.getClassId() > 0) {
            sql.append(" and user_course.userid ='" + query.getUserId() + "'");
        }
//        sql.append(" limit " + (pageRequest.getPage() - 1) * pageRequest.getLimit() + ", " + pageRequest.getLimit());

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<UserEntity> result = new ArrayList<>();
        try {
            System.out.println("Search query : " + sql.toString());
            Query bquery = session.createSQLQuery(sql.toString());
            bquery.setFirstResult((pageRequest.getPage() - 1) * pageRequest.getLimit());
            bquery.setMaxResults(pageRequest.getLimit());
            return ((List<Object>) bquery.getResultList());
        } catch (HibernateException e) {
            transaction.rollback();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public SearchResult<UserEntity> search(SearchUser query, PageRequest pageRequest) {
        SearchResult<UserEntity> res = new SearchResult<>();
        StringBuilder sql = new StringBuilder("from UserEntity as user");

        if (query.getCodeCourse() != null && query.getCodeCourse().length() >= 0) {
            sql.append(" INNER JOIN user_course as user_course on user_course.userid = user.id ");
            sql.append(" INNER JOIN course as course on course.id = user_course.courseid ");
        }
        sql.append(" where 1=1 ");
        if (!StringUtils.isEmpty(query.getFullname())) {
            sql.append(" and fullname LIKE '%" + query.getFullname() + "%'");
        }
        if (query.getClassId() != null && query.getClassId() >= 0) {
            sql.append(" and classId ='" + query.getClassId() + "'");
        }
        if (query.getCodeCourse() != null && query.getCodeCourse().length() >= 0) {
            sql.append(" and course.code ='" + query.getCodeCourse() + "'");
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
            result = (List<UserEntity>) bquery.list();
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
