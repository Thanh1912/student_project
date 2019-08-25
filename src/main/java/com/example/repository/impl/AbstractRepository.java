package com.example.repository.impl;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.example.repository.GenericRepository;
import com.example.repository.beans.Pagination;
import com.example.repository.beans.Sorting;
import com.example.utils.HibernateUtil;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AbstractRepository<ID extends Serializable, T> implements GenericRepository<ID, T> {
	
	private Class<T> persistenceClass;

	public AbstractRepository() {
		this.persistenceClass = (Class<T>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}

	public String getPersistenceClassName() {
		return persistenceClass.getSimpleName();
	}
	
	@Override
	public T findById(ID id) {
		T result = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		try {
			result = (T) session.get(persistenceClass, id);
			if (result == null) {
				throw new ObjectNotFoundException(" NOT FOUND " +id, null);
			}
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
		return result;
	}

	@Override
	public List<T> findAll() {
		List<T> list = new ArrayList<T>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		try {
			StringBuilder sql = new StringBuilder("from ");
			sql.append(this.getPersistenceClassName());
			Query query = session.createQuery(sql.toString());
			list = query.list();
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
		return list;
	}
	
	@Override
	public T update(T entity) {
		T result = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		try {
			Object object = session.merge(entity);
			result = (T) object;
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
		return result;
	}


	@Override
	public T saveOrUpdate(T entity) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.saveOrUpdate(entity);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}

	@Override
	public T save(T entity) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.persist(entity);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}
	
	@Override
	public Integer delete(List<ID> ids) {
		Integer count = 0;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		try {
			for (ID item: ids) {
				T t = (T) session.get(persistenceClass, item);
				session.delete(t);
				count++;
			}
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
			throw e;
		}
		return count;
	}

	@Override
	public void delete(ID id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		try {
			T t = (T) session.get(persistenceClass, id);
			session.delete(t);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public T findOneByProperty(String property, Object value) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		T result = null;
		try {
			String sql = " FROM "+getPersistenceClassName()+" model WHERE model."+property+"= :value";
			Query query = session.createQuery(sql.toString());
			query.setParameter("value", value);
			result = (T) query.uniqueResult();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
		return result;
	}

	@Override
	public List<T> findByProperty(String propertyName, Object value, int... rowStartIdxAndCount) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		try {
			final String queryString = "select model from " + getPersistenceClassName() + " model where model."
					+ propertyName + "= :propertyValue";
			Query query = session.createQuery(queryString);
			query.setParameter("propertyValue", value);
			if (rowStartIdxAndCount != null && rowStartIdxAndCount.length > 0) {
				int rowStartIdx = Math.max(0, rowStartIdxAndCount[0]);
				if (rowStartIdx > 0) {
					query.setFirstResult(rowStartIdx);
				}

				if (rowStartIdxAndCount.length > 1) {
					int rowCount = Math.max(0, rowStartIdxAndCount[1]);
					if (rowCount > 0) {
						query.setMaxResults(rowCount);
					}
				}
			}
			return query.list();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<T> findByProperty(Map<String, Object> properties) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Object[] nameQuery = HibernateUtil.buildNameQuery(properties);
		try {
			StringBuilder sql = new StringBuilder("FROM ");
			sql.append(getPersistenceClassName()).append(" WHERE 1=1 ").append(nameQuery[0]);
			Query query = session.createQuery(sql.toString());
			setParameterToQuery(nameQuery, query);
			return query.list();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return new ArrayList<T>();
	}

	@Override
	public List<T> findAll(Map<String, Object> searchProperty, Pagination pagination, Sorting sorting, String... addCondition) {
		List<T> list = new ArrayList<T>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Object[] nameQuery = HibernateUtil.buildNameQuery(searchProperty);
		try {
			StringBuilder sql = new StringBuilder(" FROM ");
			sql.append(getPersistenceClassName()).append(" WHERE 1=1 ").append(nameQuery[0]);
			if (addCondition != null && addCondition.length > 0) {
				sql.append(" "+addCondition[0]+" ");
			}
			if (sorting.getSortExpression() != null && sorting.getSortDirection() != null) {
				sql.append(" ORDER BY ").append(sorting.getSortExpression());
				sql.append(" " +sorting.getSortDirection());
			}
			Query query = session.createQuery(sql.toString());
			setParameterToQuery(nameQuery, query);
			if (pagination.getOffset() != null && pagination.getOffset() >= 0) {
				query.setFirstResult(pagination.getOffset());
			}
			if (pagination.getLimit() != null && pagination.getLimit() > 0) {
				query.setMaxResults(pagination.getLimit());
			}
			list = query.list();
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
		return list;
	}

	@Override
	public Object getTotalItem(Map<String, Object> searchProperty, String... addCondition) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Object totalItem = 0;
		try {
			Object[] nameQuery = HibernateUtil.buildNameQuery(searchProperty);
			StringBuilder sql = new StringBuilder("SELECT count(*) FROM ");
			sql.append(getPersistenceClassName()).append(" WHERE 1=1 ").append(nameQuery[0]);
			if (addCondition != null && addCondition.length > 0) {
				sql.append(" "+addCondition[0]+" ");
			}
			Query query = session.createQuery(sql.toString());
			setParameterToQuery(nameQuery, query);
			totalItem = query.list().get(0);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
			throw e;
		}
		return totalItem;
	}

	@Override
	public Long countByProperty(Map<String, Object> searchProperty) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Long totalItem = 0L;
		try {
			Object[] nameQuery = HibernateUtil.buildNameQuery(searchProperty);
			StringBuilder sql = new StringBuilder("SELECT count(*) FROM ");
			sql.append(getPersistenceClassName()).append(" WHERE 1=1 ").append(nameQuery[0]);
			Query query = session.createQuery(sql.toString());
			setParameterToQuery(nameQuery, query);
			totalItem = (Long) query.list().get(0);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
			throw e;
		}
		return totalItem;
	}

	private void setParameterToQuery(Object[] nameQuery, Query query) {
		if (nameQuery.length == 3) {
			String[] params = (String[]) nameQuery[1];
			Object[] values = (Object[]) nameQuery[2];
			for (int i2 = 0; i2 < params.length ; i2++) {
				query.setParameter(params[i2], values[i2]);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findOneByProperty(Map<String, Object> properties) {
		T result = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Object[] nameQuery = HibernateUtil.buildNameQueryEqual(properties);
		try {
			StringBuilder sql = new StringBuilder("FROM ");
			sql.append(getPersistenceClassName()).append(" WHERE 1=1 ").append(nameQuery[0]);
			Query query = session.createQuery(sql.toString());
			setParameterToQuery(nameQuery, query);
			result = (T) query.uniqueResult();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return result;
	}
}
