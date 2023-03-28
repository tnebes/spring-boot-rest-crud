package hr.tnebes.crud;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.validation.Validation;
import javax.validation.Validator;

@Configuration
public class AppConfig {

    @Bean
    public Validator validator() {
        System.out.println("Validator bean created");
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

}