package com.talisman.model;

import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class Paginate<E> {

    private int totalRecords;
    private int currentPage;
    private List<E> records;
    private int maxRecords;
    private int totalPages;

    private int maxNavigationPages;
    private List<Integer> navigationPages;

    public Paginate(final Query query, final int page, final int maxResult, final int maxNavigationPages) {

        final int pageIndex = ((page - 1) < 0) ? 0 : (page - 1);
        int fromRecordIndex = pageIndex * maxResult;
        int maxRecordIndex = fromRecordIndex + maxResult;

        ScrollableResults resultsScroll = query.scroll(ScrollMode.SCROLL_INSENSITIVE);
        List<E> results = new ArrayList<E>();
        boolean hasResults =resultsScroll.first();

        if(hasResults) {
            hasResults = resultsScroll.scroll(fromRecordIndex);
            if(hasResults) {
                do {
                    E record = (E) resultsScroll.get(0);
                    results.add(record);
                } while (resultsScroll.next()//
                        && resultsScroll.getRowNumber() >= fromRecordIndex
                        && resultsScroll.getRowNumber() < maxRecordIndex);
            }
            resultsScroll.last();
        }
        this.totalRecords = resultsScroll.getRowNumber() + 1;
        this.currentPage = pageIndex + 1;
        this.records = results;
        this.maxRecords = maxResult;

        this.totalPages = (this.totalRecords / this.maxRecords) + 1;
        this.maxNavigationPages = maxNavigationPages;

        if (maxNavigationPages < totalPages) {
            this.maxNavigationPages = maxNavigationPages;
        }
        this.calculateNavigationPages();
    }

    private void calculateNavigationPages() {

        this.navigationPages = new ArrayList<Integer>();

        int current = this.currentPage > this.totalPages ? this.totalPages : this.currentPage;

        int begin = current - this.maxNavigationPages / 2;
        int end = current + this.maxNavigationPages / 2;

        navigationPages.add(1);
        if (begin > 2) {
            navigationPages.add(-1);
        }

        for (int i = begin; i < end; i++) {
            if (i > 1 && i < this.totalPages) {
                navigationPages.add(i);
            }
        }

        if (end < this.totalPages - 2) {
            navigationPages.add(-1);
        }
        navigationPages.add(this.totalPages);
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public int getTotalRecords() {
        return this.totalRecords;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public List<E> getRecords() {
        return this.records;
    }

    public int getMaxResult() {
        return this.maxRecords;
    }

    public List<Integer> getNavigationPages() {
        return this.navigationPages;
    }
}
