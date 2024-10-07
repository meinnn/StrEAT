package io.ssafy.p.j11a307.store.global;

import lombok.Getter;

@Getter
public class PagedDataResponse<T> extends DataResponse<T> {
    private final long totalElements;
    private final int lastPage;
    private final int currentPage;

    public PagedDataResponse(String message, T data, long totalElements, int lastPage, int currentPage) {
        super(message, data);
        this.totalElements = totalElements;
        this.lastPage = lastPage;
        this.currentPage = currentPage;
    }

    public static <T> PagedDataResponse<T> of(String message, T data, long totalElements, int lastPage, int currentPage) {
        return new PagedDataResponse<>(message, data, totalElements, lastPage, currentPage);
    }
}
