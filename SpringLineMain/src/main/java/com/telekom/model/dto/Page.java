package com.telekom.model.dto;

import java.util.List;

public class Page<T> {

    private List<T> data;
    private Integer currentPage;
    private Integer totalPages;
    private Integer lastPage;


    public Page(List<T> data, Integer currentPage, Integer totalPages, Integer lastPage) {
        this.data = data;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.lastPage = lastPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getLastPage() {
        return lastPage;
    }

    public void setLastPage(Integer lastPage) {
        this.lastPage = lastPage;
    }
}
