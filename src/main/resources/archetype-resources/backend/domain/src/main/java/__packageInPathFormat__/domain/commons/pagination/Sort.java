package ${package}.domain.commons.pagination;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Sort implements Serializable {
    public enum Direction { ASC, DESC }

    public static final class Order implements Serializable {
        private final Direction direction;
        private final String property;

        public Order(Direction direction, String property) {
            this.direction = Objects.requireNonNull(direction);
            this.property = Objects.requireNonNull(property);
        }

        public Direction direction() { return direction; }
        public String property() { return property; }
    }

    private final List<Order> orders;

    private Sort(List<Order> orders) {
        this.orders = Collections.unmodifiableList(new ArrayList<>(orders));
    }

    public static Sort by(List<Order> orders) { return new Sort(orders); }
    public static Sort unsorted() { return new Sort(List.of()); }

    public List<Order> orders() { return orders; }
    public boolean isSorted() { return !orders.isEmpty(); }
}
