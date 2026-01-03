package ${package}.domain.commons.pagination;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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
public final class Sort implements Serializable {

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
    public static final class Order implements Serializable {

        private final Direction direction;
        private final String property;

        /**
         * Creates a new sort order.
         *
         * @param direction the sort direction
         * @param property the property name to sort by
         */
        public Order(Direction direction, String property) {
            this.direction = Objects.requireNonNull(direction, "direction");
            this.property = Objects.requireNonNull(property, "property");
        }

        /**
         * Returns the sort direction.
         *
         * @return the {@link Direction}
         */
        public Direction direction() {
            return direction;
        }

        /**
         * Returns the property name to sort by.
         *
         * @return the property name
         */
        public String property() {
            return property;
        }
    }

    private final List<Order> orders;

    private Sort(List<Order> orders) {
        this.orders = Collections.unmodifiableList(new ArrayList<>(orders));
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
     * Returns the list of sort orders.
     *
     * @return an unmodifiable list of {@link Order}
     */
    public List<Order> orders() {
        return orders;
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
