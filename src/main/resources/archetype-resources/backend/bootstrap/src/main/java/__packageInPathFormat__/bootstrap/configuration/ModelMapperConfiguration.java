package ${package}.bootstrap.configuration;

import ${package}.presentation.common.qualifier.PresentationConverter;
import ${package}.external.common.qualifier.ExternalConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ModelMapperConfiguration {

    private static ModelMapper base() {
        var modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(AccessLevel.PRIVATE)
                .setSkipNullEnabled(true);
        return modelMapper;
    }

    @Bean
    @Qualifier("presentationMapper")
    public ModelMapper presentationMapper(
            @PresentationConverter List<Converter<?, ?>> converters
    ) {
        var modelMapper = base();
        converters.forEach(modelMapper::addConverter);
        return modelMapper;
    }

    @Bean
    @Qualifier("externalMapper")
    public ModelMapper externalMapper(
            @ExternalConverter List<Converter<?, ?>> converters
    ) {
        var modelMapper = base();
        converters.forEach(modelMapper::addConverter);
        return modelMapper;
    }
}
