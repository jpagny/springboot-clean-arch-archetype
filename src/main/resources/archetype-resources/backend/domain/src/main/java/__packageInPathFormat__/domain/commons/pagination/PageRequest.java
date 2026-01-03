package ${package}.domain.commons.pagination;

import java.io.Serializable;

public record PageRequest(int page, int size, Sort sort) implements Serializable {
    public PageRequest {
        if (page < 0) throw new IllegalArgumentException("page must be >= 0");
        if (size <= 0) throw new IllegalArgumentException("size must be > 0");
        sort = (sort == null) ? Sort.unsorted() : sort;
    }

    public static PageRequest of(int page, int size) { return new PageRequest(page, size, Sort.unsorted()); }
    public static PageRequest of(int page, int size, Sort sort) { return new PageRequest(page, size, sort); }
}
