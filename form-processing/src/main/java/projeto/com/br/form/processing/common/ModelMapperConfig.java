package projeto.com.br.form.processing.common;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import projeto.com.br.form.processing.api.dto.user.RegisterDTO;
import projeto.com.br.form.processing.domain.model.user.User;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(RegisterDTO.class, User.class).setConverter(context -> {
            RegisterDTO source = context.getSource();
            User target = new User();
            target.setName(source.name());
            target.setEmail(source.email());
            target.setPassword(source.password());
            return target;
        });

        return modelMapper;
    }
}
