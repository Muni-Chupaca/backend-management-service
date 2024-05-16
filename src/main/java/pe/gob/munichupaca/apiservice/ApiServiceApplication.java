package pe.gob.munichupaca.apiservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import pe.gob.munichupaca.apiservice.auth.domain.models.entities.RoleEntity;
import pe.gob.munichupaca.apiservice.auth.domain.models.entities.UserEntity;
import pe.gob.munichupaca.apiservice.auth.domain.models.enums.RoleEnum;
import pe.gob.munichupaca.apiservice.auth.infrastructure.repositories.UserRepository;

import java.util.Set;

@RequiredArgsConstructor
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Municipalidad Provincial de Chupaca - API", description = "Service | Vehicle Management application REST API documentation.", version = "1.0.0"))
public class ApiServiceApplication {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public static void main(String[] args) {

        SpringApplication.run(ApiServiceApplication.class, args);

        String url = "http://localhost:8080/swagger-ui/index.html";
        System.out.println("\n• Swagger UI is available at » " + url);
    }

    @Bean
    CommandLineRunner init() {
        return args -> {
            System.out.println("\n• Application is running...\n");

            UserEntity userEntity = UserEntity.builder()
                    .email("wilver.ar.dev@gmail.com")
                    .username("wilver-ar")
                    .password(passwordEncoder.encode("12345678"))
                    .roles(Set.of(RoleEntity.builder()
                            .role(RoleEnum.valueOf(RoleEnum.ADMIN.name()))
                            .build()))
                    .build();

            userRepository.save(userEntity);
        };
    }
}
