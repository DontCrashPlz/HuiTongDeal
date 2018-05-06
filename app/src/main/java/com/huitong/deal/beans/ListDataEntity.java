package com.huitong.deal.beans;

import java.util.ArrayList;

/**
 * Created by Zheng on 2018/5/6.
 */

public class ListDataEntity<T,K> {
    //总记录数
    private int totalRow;
    //当前页码
    private int pageNumber;
    //last
    private boolean last;
    //当前记录数
    private int currentPageCount;
    //开始行
    private int startRow;
    //是否有下一页
    private boolean hasNextPage;
    //总页数
    private int totalPage;
    //每页展示的记录数
    private int pageSize;
    //结束记录数
    private int endRow;
    //数据列表
    private ArrayList<T> list;
    //uri
    private String uri;
    //orderMode
    private String orderMode;
    //extData
    private String extData;
    //orderColunm
    private String orderColunm;
    //hasPreviousPage
    private boolean hasPreviousPage;
    private K queryParam;
    //first
    private boolean first;

    public int getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public int getCurrentPageCount() {
        return currentPageCount;
    }

    public void setCurrentPageCount(int currentPageCount) {
        this.currentPageCount = currentPageCount;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public ArrayList<T> getList() {
        return list;
    }

    public void setList(ArrayList<T> list) {
        this.list = list;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getOrderMode() {
        return orderMode;
    }

    public void setOrderMode(String orderMode) {
        this.orderMode = orderMode;
    }

    public String getExtData() {
        return extData;
    }

    public void setExtData(String extData) {
        this.extData = extData;
    }

    public String getOrderColunm() {
        return orderColunm;
    }

    public void setOrderColunm(String orderColunm) {
        this.orderColunm = orderColunm;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public K getQueryParam() {
        return queryParam;
    }

    public void setQueryParam(K queryParam) {
        this.queryParam = queryParam;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    @Override
    public String toString() {
        return "ListDataEntity{" +
                "totalRow=" + totalRow +
                ", pageNumber=" + pageNumber +
                ", last=" + last +
                ", currentPageCount=" + currentPageCount +
                ", startRow=" + startRow +
                ", hasNextPage=" + hasNextPage +
                ", totalPage=" + totalPage +
                ", pageSize=" + pageSize +
                ", endRow=" + endRow +
                ", list=" + list +
                ", uri='" + uri + '\'' +
                ", orderMode='" + orderMode + '\'' +
                ", extData='" + extData + '\'' +
                ", orderColunm='" + orderColunm + '\'' +
                ", hasPreviousPage=" + hasPreviousPage +
                ", queryParam=" + queryParam +
                ", first=" + first +
                '}';
    }
}
