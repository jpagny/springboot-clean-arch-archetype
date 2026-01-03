package ${package}.domain.commons.pagination;

import java.io.Serializable;

/**
 * Immutable pagination request.
 *
 * <p>
 * This record represents pagination and sorting parameters used to
 * request a page of results from a repository or query use case.
 * It is designed to be framework-agnostic and reusable across layers.
 * </p>
 *
 * @param page the requested page index (zero-based)
 * @param size the requested page size
 * @param sort the sorting information
 */
public record PageRequest(int page, int size, Sort sort) implements Serializable {

    /**
     * Creates a new {@link PageRequest} and validates pagination parameters.
     *
     * @throws IllegalArgumentException if page or size values are invalid
     */
    public PageRequest {
        if (page < 0) {
            throw new IllegalArgumentException("page must be >= 0");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("size must be > 0");
        }
        sort = (sort == null) ? Sort.unsorted() : sort;
    }

    /**
     * Creates a {@link PageRequest} without sorting.
     *
     * @param page the requested page index (zero-based)
     * @param size the requested page size
     * @return a new {@link PageRequest}
     */
    public static PageRequest of(int page, int size) {
        return new PageRequest(page, size, Sort.unsorted());
    }

    /**
     * Creates a {@link PageRequest} with sorting.
     *
     * @param page the requested page index (zero-based)
     * @param size the requested page size
     * @param sort the sorting information
     * @return a new {@link PageRequest}
     */
    public static PageRequest of(int page, int size, Sort sort) {
        return new PageRequest(page, size, sort);
    }
}
