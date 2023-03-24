package hr.tnebes.crud;

import hr.tnebes.crud.services.FakerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
        "hr.tnebes.crud"})
@EntityScan(basePackages = "hr.tnebes.crud.models")
@EnableJpaRepositories(basePackages = "hr.tnebes.crud.repository")
@Slf4j
public class CrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(FakerService fakerService) {
        return args -> fakerService.generateProducts(25);
    }

}