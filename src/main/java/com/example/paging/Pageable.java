package com.example.paging;

public interface Pageable {
    int getPage();
    int getOffset();
    int getLimit();
}
