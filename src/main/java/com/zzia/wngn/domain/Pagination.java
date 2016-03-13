package com.zzia.wngn.domain;

import java.io.Serializable;

public class Pagination implements Serializable{
	
	private static final long serialVersionUID = 6667781413321001295L;
	
	/** 每页记录数 */
    private int pageSize = 10;
    /** 分页中间显示页码数 */
    private int toShowPageNum = 5;
    /** 总记录数 */
    private int recordNum;
    /** 总页数 */
    private int totalPages;
    /** 当前页 */
    private int curPage = 1;
    /** 分页中间显示页码数开始页码 */
    private int toShowBeginPageNo = 1;
    /** 分页中间显示页码数结束页码 */
    private int toShowEndPageNo = 5;

    public int getToShowPageNum() {
        return toShowPageNum;
    }

    public void setToShowPageNum(int toShowPageNum) {
        this.toShowPageNum = toShowPageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurPage() {
        return curPage;
    }

    public void clear() {
        pageSize = 10;
        toShowPageNum = 5;

        recordNum = 0;
        totalPages = 1;

        curPage = 1;
        toShowBeginPageNo = 1;
        toShowEndPageNo = 5;
    }

    public void setCurPage(int curPage) {
        if (recordNum == 0) {
            this.curPage = 1;
            toShowBeginPageNo = 1;
            toShowEndPageNo = 1;
            return;
        }
        this.curPage = curPage;
        if (curPage <= 0) {
            setCurPage(1);
        } else if (curPage > totalPages) {
            setCurPage(totalPages);
        } else {
            if (totalPages <= toShowPageNum) {
                toShowBeginPageNo = 1;
                toShowEndPageNo = totalPages;
            } else {
                if (curPage <= Math.floor(toShowPageNum / 2)) {
                    toShowBeginPageNo = 1;
                    toShowEndPageNo = toShowPageNum;

                } else if (curPage >= totalPages
                        - Math.floor(toShowPageNum / 2)) {
                    toShowBeginPageNo = totalPages - toShowPageNum + 1;
                    toShowEndPageNo = totalPages;
                } else {
                    toShowBeginPageNo = (int) (curPage - Math
                            .floor(toShowPageNum / 2));
                    toShowEndPageNo = (int) (curPage + Math
                            .floor(toShowPageNum / 2));
                }
            }
        }
    }

    public void next() {
        setCurPage(curPage + 1);
    }

    public void previous() {
        setCurPage(curPage - 1);
    }

    public int getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(int recordNum) {
        if (recordNum <= 0) {

            this.recordNum = 0;
            setTotalPages(1);
        }
        else {

            this.recordNum = recordNum;
            setTotalPages((recordNum - 1) / this.pageSize + 1);
        }
    }

    public int getToShowBeginPageNo() {
        return toShowBeginPageNo;
    }

    public void setToShowBeginPageNo(int toShowBeginPageNo) {
        this.toShowBeginPageNo = toShowBeginPageNo;
    }

    public int getToShowEndPageNo() {
        return toShowEndPageNo;
    }

    public void setToShowEndPageNo(int toShowEndPageNo) {
        this.toShowEndPageNo = toShowEndPageNo;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Pagination page = new Pagination();
        page.setRecordNum(0);
        page.setCurPage(10);
        System.out.println(page.getToShowBeginPageNo() + ","
                + page.getCurPage() + "," + page.getToShowEndPageNo() + "," + page.getTotalPages());
        page.previous();
        System.out.println(page.getToShowBeginPageNo() + ","
                + page.getCurPage() + "," + page.getToShowEndPageNo() + "," + page.getTotalPages());
        page.previous();
        System.out.println(page.getToShowBeginPageNo() + ","
                + page.getCurPage() + "," + page.getToShowEndPageNo() + "," + page.getTotalPages());
        page.previous();
        System.out.println(page.getToShowBeginPageNo() + ","
                + page.getCurPage() + "," + page.getToShowEndPageNo() + "," + page.getTotalPages());
        page.previous();
        System.out.println(page.getToShowBeginPageNo() + ","
                + page.getCurPage() + "," + page.getToShowEndPageNo() + "," + page.getTotalPages());

    }

}
