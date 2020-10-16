package com.opdar.appversion.base;

public class Page<T> {
    private Integer count;
    private Integer pageNo;
    private Integer pageSize;
    private Integer pageCount;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setCount(Integer count) {
        this.count = count;
        int retain = count % pageSize;
        int pageCount = count/pageSize;
        if(retain > 0){
            pageCount+=1;
        }
        this.pageCount = pageCount;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getCount() {
        return count;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageSize() {
        return pageSize;
    }
}
