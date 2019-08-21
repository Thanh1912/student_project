package com.example.repository.beans;

import java.io.Serializable;

public class Pagination implements Serializable {

	private static final long serialVersionUID = -724112660828272667L;

	private Integer offset;
	private Integer limit;

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}
}
