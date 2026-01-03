package ${package}.domain.commons.exceptions.engine;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to define an error context for business errors or exceptions.
 *
 * <p>
 * The error context represents a functional or bounded context identifier
 * (for example: "user", "order", "payment") that can be used to enrich
 * error reporting, logging, monitoring, or API responses.
 * </p>
 *
 * <p>
 * This annotation is typically applied to:
 * <ul>
 *   <li>Business error enums</li>
 *   <li>Domain-specific exception classes</li>
 * </ul>
 * </p>
 *
 * <p>
 * The context value is intended to be stable and human-readable, and must
 * not contain technical or infrastructure-specific information.
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ErrorContext {

    /**
     * Defines the error context identifier.
     *
     * @return the context value
     */
    String value();
}
