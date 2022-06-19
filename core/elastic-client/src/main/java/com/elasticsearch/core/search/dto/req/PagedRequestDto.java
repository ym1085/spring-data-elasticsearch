package com.elasticsearch.core.search.dto.req;

public class PagedRequestDto {

    private final int DEFAULT_PAGE_SIZE = 200;
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
