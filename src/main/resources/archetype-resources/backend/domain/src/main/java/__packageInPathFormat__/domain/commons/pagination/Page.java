package ${package}.domain.commons.pagination;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Framework-free page result model.
 */
public record Page<T>(
        List<T> content,
        long totalElements,
        int totalPages,
        int page,
        int size,
        Sort sort
) implements Serializable {
    public Page {
        Objects.requireNonNull(content, "content");
        if (page < 0) throw new IllegalArgumentException("page must be >= 0");
        if (size <= 0) throw new IllegalArgumentException("size must be > 0");
        if (totalElements < 0) throw new IllegalArgumentException("totalElements must be >= 0");
        if (totalPages < 0) throw new IllegalArgumentException("totalPages must be >= 0");
        sort = (sort == null) ? Sort.unsorted() : sort;
    }

    public boolean hasNext() { return page + 1 < totalPages; }
    public boolean hasPrevious() { return page > 0; }
}
