package com.example.repository.impl;

import com.example.entity.UserCourseEntity;
import com.example.entity.UserEntity;
import com.example.paging.SearchResult;
import com.example.repository.IUserCourseRepository;
import com.example.utils.HibernateUtil;
import com.example.utils.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;


public class UserCourseRepository extends AbstractRepository<Long, UserCourseEntity> implements IUserCourseRepository {

    public UserCourseRepository() {
    }

    @Override
    public List<UserCourseEntity> findByClassIdAndCourseId(Long classId, Long courseId) {
        StringBuilder sql = new StringBuilder("from UserCourseEntity as user_course");
        sql.append(" where ");
        sql.append(" user_course.courseEntity.id ='" + courseId + "'");
        sql.append(" and user_course.userEntity.classId ='" + classId + "'");

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            System.out.println("Search query : " + sql.toString());
            Query bquery = session.createQuery(sql.toString());
            return  (List<UserCourseEntity>)  bquery.list();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

  /*  @Override
    public List<Object> findByClassIdAndCourseId(Long classId, Long courseId) {
        StringBuilder sql = new StringBuilder("select * from user_course as user_course");
        sql.append(" INNER JOIN user as user ");
        sql.append(" where user_course.userid = user.id");
        sql.append(" and user_course.courseid = " + courseId + "");
        sql.append(" and user.classId = " + classId + "");

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            System.out.println("Search query : " + sql.toString());
            Query bquery = session.createSQLQuery(sql.toString());
            return  (List<Object>)  bquery.list();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }*/
}
