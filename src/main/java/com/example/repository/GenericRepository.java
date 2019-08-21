package com.example.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.example.repository.beans.Pagination;
import com.example.repository.beans.Sorting;

public interface GenericRepository<ID extends Serializable, T> {
	T findById(ID id);
	List<T> findAll();
	T update(T entity);
	T save(T entity);
	Integer delete(List<ID> ids);
	void delete(ID id);
	T findOneByProperty(String property, Object value);
	List<T> findByProperty(String propertyName, Object value, int... rowStartIdxAndCount);
	List<T> findByProperty(Map<String, Object> properties);
	List<T> findAll(Map<String, Object> searchProperty, Pagination pagination, Sorting sorting, String... addCondition);
	Object getTotalItem(Map<String, Object> searchProperty, String... addCondition);
	Long countByProperty(Map<String, Object> searchProperty);
	T findOneByProperty(Map<String, Object> properties);
}
