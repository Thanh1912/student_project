package com.example.repository.impl;

import com.example.dto.SearchCourse;
import com.example.entity.CourseEntity;
import com.example.entity.UserCourseEntity;
import com.example.paging.PageRequest;
import com.example.paging.SearchResult;
import com.example.repository.ICourseRepository;
import com.example.utils.HibernateUtil;
import com.example.utils.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CourseRepository extends AbstractRepository<Long, CourseEntity> implements ICourseRepository {

    public CourseRepository() {
    }

    @Override
    public SearchResult<List<Object>> search(SearchCourse query, PageRequest pageRequest) {
        SearchResult<List<Object>> res = new SearchResult<>();
        StringBuilder sql = new StringBuilder("select DISTINCT course.id, course.code, course.room, course.name, course.createddate, course.modifieddate  from course as course");
        sql.append(" INNER JOIN user_course as user_course on user_course.courseid = course.id ");
        sql.append(" INNER JOIN user as user on user_course.userid = user.id ");
        sql.append(" INNER JOIN classes as class on class.id = user.classId ");
        sql.append(" where 1=1 ");
        if (!StringUtils.isEmpty(query.getCode())) {
            sql.append(" and course.code LIKE '%" + query.getName() + "%'");
        }
        if (!StringUtils.isEmpty(query.getName())) {
            sql.append(" and course.name LIKE '%" + query.getName() + "%'");
        }
        if (!StringUtils.isEmpty(query.getClassName())) {
            sql.append(" and class.name =  " + query.getClassName());
        }
        if (query.getUserId() != null && query.getUserId() > 0) {
            sql.append(" and user.id =  " + query.getUserId());
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            System.out.println("Search query : " + sql.toString());
            Query bquery = session.createSQLQuery(sql.toString());
            bquery.setFirstResult((pageRequest.getPage() - 1) * pageRequest.getLimit());
            bquery.setMaxResults(pageRequest.getLimit());
            List<Object> list = (List<Object>) bquery.getResultList();
            res.setResults(Collections.singletonList(list));
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            session.close();
        }
        return res;
    }

    @Override
    public UserCourseEntity findByUserId(Long userId, Long courseId) {
        StringBuilder sql = new StringBuilder("from UserCourseEntity");
        sql.append(" where ");
        sql.append(" userid = " + userId + "");
        sql.append(" and courseid = " + courseId + "");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<UserCourseEntity> result = new ArrayList<>();
        try {
            System.out.println("Search query : " + sql.toString());
            Query bquery = session.createQuery(sql.toString());
            result = (List<UserCourseEntity>) bquery.list();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            session.close();
        }
        return result.size() == 0 ? null : result.get(0);
    }

}
