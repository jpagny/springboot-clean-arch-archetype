package ${package}.transport.common.qualifier;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Spring qualifier used to identify presentation-level converters or mappers.
 *
 * <p>
 * This annotation is used to distinguish converters or mappers responsible
 * for transforming application or domain results into transport-level
 * representations (DTOs, API responses), and vice versa.
 * </p>
 *
 * <p>
 * It belongs to the transport layer and helps separate presentation concerns
 * from infrastructure or domain mappings when multiple beans of the same
 * type are present in the Spring context.
 * </p>
 */
@Target({ElementType.TYPE, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Qualifier("presentationMapper")
public @interface PresentationConverter {
}
