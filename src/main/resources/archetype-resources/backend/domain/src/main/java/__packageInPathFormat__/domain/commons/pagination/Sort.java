package ${package}.domain.commons.pagination;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Immutable sorting definition used for pagination and queries.
 *
 * <p>
 * This class represents sorting instructions applied to a query,
 * independent of any persistence or framework-specific implementation.
 * </p>
 */
public record Sort(List<Order> orders) implements Serializable {

    /**
     * Sorting direction.
     */
    public enum Direction {
        ASC,
        DESC
    }

    /**
     * Single sort order definition.
     *
     * <p>
     * Each order defines a property and a direction.
     * </p>
     */
    public record Order(Direction direction, String property) implements Serializable {

        public Order {
            Objects.requireNonNull(direction, "direction");
            Objects.requireNonNull(property, "property");
        }
    }

    /**
     * Canonical constructor with defensive copy.
     */
    public Sort {
        orders = List.copyOf(Objects.requireNonNull(orders, "orders"));
    }

    /**
     * Creates a {@link Sort} with the given orders.
     *
     * @param orders the list of sort orders
     * @return a new {@link Sort}
     */
    public static Sort by(List<Order> orders) {
        return new Sort(orders);
    }

    /**
     * Creates an unsorted {@link Sort}.
     *
     * @return an unsorted {@link Sort}
     */
    public static Sort unsorted() {
        return new Sort(List.of());
    }

    /**
     * Indicates whether sorting is applied.
     *
     * @return {@code true} if sorted, {@code false} otherwise
     */
    public boolean isSorted() {
        return !orders.isEmpty();
    }
}
