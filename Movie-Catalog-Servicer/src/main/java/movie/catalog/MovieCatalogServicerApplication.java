package movie.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class MovieCatalogServicerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieCatalogServicerApplication.class, args);
	}

}
