package com.example.repository.beans;

import java.io.Serializable;

public class Sorting implements Serializable {

	private static final long serialVersionUID = -7357035689407713037L;

	private String sortExpression;
	private String sortDirection;

	public String getSortExpression() {
		return sortExpression;
	}

	public void setSortExpression(String sortExpression) {
		this.sortExpression = sortExpression;
	}

	public String getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}
}
