package be.ucll.robbes.cityquest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableDiscoveryClient
@SpringBootApplication
@EnableDiscoveryClient
public class CityquestApplication {

	public static void main(String[] args) {
		SpringApplication.run(CityquestApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	/// Why: Otherwise we get a Access-Control-Allow-Origin error
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")/*.allowedOrigins("*")*/;
			}
		};
	}
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
