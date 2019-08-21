package com.example.paging;

public class PageRequest implements Pageable {

    private int page; // page index
    private int maxPageItem; // pageSize

    public PageRequest() {
    }

    public PageRequest(int page, int maxPageItem) {
        this.page = page;
        this.maxPageItem = maxPageItem;
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public int getOffset() {
        return (this.page - 1) * this.maxPageItem;
    }

    @Override
    public int getLimit() {
        return maxPageItem;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getMaxPageItem() {
        return maxPageItem;
    }

    public void setMaxPageItem(int maxPageItem) {
        this.maxPageItem = maxPageItem;
    }
}
