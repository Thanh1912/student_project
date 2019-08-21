package com.example.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class AbstractDTO<T> implements Serializable {

	private static final long serialVersionUID = 7933844494198236496L;

	private Long id;
	private Timestamp createdDate;
	private Timestamp modifiedDate;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
}
