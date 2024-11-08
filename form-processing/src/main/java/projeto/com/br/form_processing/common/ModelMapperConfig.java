package projeto.com.br.form_processing.common;

import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
