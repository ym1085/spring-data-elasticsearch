package com.elasticsearch.search;

public class PagedRequestDTO {
    private static final int DEFAULT_PAGE_SIZE = 100;

    private int page;
    private int size;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size != 0 ? size : DEFAULT_PAGE_SIZE;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
