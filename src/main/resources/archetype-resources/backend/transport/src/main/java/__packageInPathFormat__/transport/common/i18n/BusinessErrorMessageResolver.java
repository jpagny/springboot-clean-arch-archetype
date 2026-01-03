package ${package}.transport.common.i18n;

import ${package}.domain.commons.exceptions.engine.IBusinessError;

import java.util.Locale;

/**
 * Contract for resolving localized messages for business errors.
 *
 * <p>
 * This interface defines how domain-level {@link IBusinessError} definitions
 * are translated into human-readable, localized messages.
 * </p>
 *
 * <p>
 * It belongs to the transport layer and acts as a boundary between the
 * domain error model and internationalization (i18n) concerns.
 * </p>
 *
 * <p>
 * Implementations typically rely on message bundles, message sources,
 * or other localization mechanisms available at the transport or API level.
 * </p>
 */
public interface BusinessErrorMessageResolver {

    /**
     * Resolves a localized message for the given business error.
     *
     * @param error the business error definition
     * @param locale the target locale
     * @param args optional arguments used for message formatting
     * @return the localized error message
     */
    String resolve(IBusinessError error, Locale locale, Object... args);
}
