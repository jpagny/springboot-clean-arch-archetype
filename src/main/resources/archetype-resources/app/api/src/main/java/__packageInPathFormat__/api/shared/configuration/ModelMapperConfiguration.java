package ${package}.api.shared.configuration;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

    private final MessageSource messageSource;

    public ModelMapperConfiguration(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(AccessLevel.PRIVATE)
                .setSkipNullEnabled(true);

        configureDtoToModel(modelMapper);
        configureModelToEntity(modelMapper);
        configureEntityToModel(modelMapper);
        configureModelToDto(modelMapper);

        return modelMapper;
    }

    private void configureDtoToModel(ModelMapper mm) { }

    private void configureModelToEntity(ModelMapper mm) { }

    private void configureEntityToModel(ModelMapper mm) { }

    private void configureModelToDto(ModelMapper mm) {
    }

    private <S, D> void register(ModelMapper mm, Converter<S, D> converter) {
        mm.addConverter(converter);
    }
}
