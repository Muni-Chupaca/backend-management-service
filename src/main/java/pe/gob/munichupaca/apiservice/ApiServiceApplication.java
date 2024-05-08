package pe.gob.munichupaca.apiservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(ApiServiceApplication.class, args);

        String url = "http://localhost:8080/swagger-ui.html";
        System.out.println("\n• Swagger UI is available at » " + url);
    }

}
