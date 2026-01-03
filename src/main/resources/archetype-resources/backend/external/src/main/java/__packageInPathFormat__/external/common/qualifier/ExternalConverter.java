package ${package}.external.common.qualifier;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Spring qualifier used to mark external converters.
 *
 * <p>
 * This annotation is used to distinguish converters that belong
 * to the external (infrastructure) layer, especially when multiple beans
 * of the same type are available in the Spring context.
 * </p>
 *
 * <p>
 * Typical use cases include:
 * <ul>
 *   <li>Persistence mappers (Domain ↔ Database)</li>
 *   <li>External system adapters</li>
 *   <li>Infrastructure-specific converters</li>
 * </ul>
 * </p>
 */
@Target({ElementType.TYPE, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Qualifier("externalMapper")
public @interface ExternalConverter {
}
