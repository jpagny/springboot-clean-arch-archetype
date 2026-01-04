package ${package}.bootstrap.configuration;

import ${package}.transport.common.qualifier.PresentationConverter;
import ${package}.external.common.qualifier.ExternalConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Bootstrap configuration for {@link ModelMapper} instances.
 *
 * <p>
 * This configuration defines and wires multiple {@link ModelMapper} beans,
 * each dedicated to a specific architectural concern:
 * </p>
 *
 * <ul>
 *   <li><strong>Presentation mapper</strong> – used for API / transport-level
 *       mappings (DTO ↔ command / response)</li>
 *   <li><strong>External mapper</strong> – used for infrastructure-level
 *       mappings (domain ↔ persistence / external systems)</li>
 * </ul>
 *
 * <p>
 * By separating mappers and qualifying their converters, this configuration
 * prevents accidental cross-layer coupling and enforces clean boundaries
 * between layers.
 * </p>
 */
@Configuration
public class ModelMapperConfiguration {

    /**
     * Creates a base {@link ModelMapper} instance with shared configuration.
     *
     * <p>
     * The base configuration enables:
     * <ul>
     *   <li>Field-based mapping</li>
     *   <li>Private field access</li>
     *   <li>Null-skipping during mapping</li>
     * </ul>
     * </p>
     *
     * @return a configured {@link ModelMapper}
     */
    private static ModelMapper base() {
        var modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(AccessLevel.PRIVATE)
                .setSkipNullEnabled(true);
        return modelMapper;
    }

    /**
     * Defines the {@link ModelMapper} used for presentation-layer mappings.
     *
     * <p>
     * Only converters annotated with {@link PresentationConverter} are
     * registered in this mapper.
     * </p>
     *
     * @param converters presentation-level converters
     * @return the presentation {@link ModelMapper}
     */
    @Bean
    public ModelMapper presentationMapper(
            @PresentationConverter List<Converter<?, ?>> converters
    ) {
        return mapperWith(converters);
    }

    /**
     * Defines the {@link ModelMapper} used for external/infrastructure mappings.
     *
     * <p>
     * Only converters annotated with {@link ExternalConverter} are registered
     * in this mapper.
     * </p>
     *
     * @param converters external-level converters
     * @return the external {@link ModelMapper}
     */
    @Bean
    public ModelMapper externalMapper(
            @ExternalConverter List<Converter<?, ?>> converters
    ) {
        return mapperWith(converters);
    }

    private ModelMapper mapperWith(List<Converter<?, ?>> converters) {
        var modelMapper = base();
        converters.forEach(modelMapper::addConverter);
        return modelMapper;
    }

}
