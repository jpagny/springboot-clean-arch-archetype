package ${package}.domain.commons.pagination;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Immutable representation of a paginated result.
 *
 * <p>
 * This record represents a page of elements returned by a query,
 * including pagination metadata. It is designed to be framework-agnostic
 * and usable across all layers of the application.
 * </p>
 *
 * @param <T> the type of elements contained in the page
 * @param content the page content
 * @param totalElements the total number of elements across all pages
 * @param totalPages the total number of available pages
 * @param page the current page index (zero-based)
 * @param size the page size
 * @param sort the sorting information applied to the page
 */
public record Page<T>(
        List<T> content,
        long totalElements,
        int totalPages,
        int page,
        int size,
        Sort sort
) implements Serializable {

    /**
     * Creates a new {@link Page} instance and validates pagination invariants.
     *
     * @throws NullPointerException if {@code content} is {@code null}
     * @throws IllegalArgumentException if pagination values are invalid
     */
    public Page {
        Objects.requireNonNull(content, "content");

        if (page < 0) {
            throw new IllegalArgumentException("page must be >= 0");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("size must be > 0");
        }
        if (totalElements < 0) {
            throw new IllegalArgumentException("totalElements must be >= 0");
        }
        if (totalPages < 0) {
            throw new IllegalArgumentException("totalPages must be >= 0");
        }

        sort = (sort == null) ? Sort.unsorted() : sort;
    }

    /**
     * Indicates whether there is a next page available.
     *
     * @return {@code true} if a next page exists, {@code false} otherwise
     */
    public boolean hasNext() {
        return page + 1 < totalPages;
    }

    /**
     * Indicates whether there is a previous page available.
     *
     * @return {@code true} if a previous page exists, {@code false} otherwise
     */
    public boolean hasPrevious() {
        return page > 0;
    }
}
