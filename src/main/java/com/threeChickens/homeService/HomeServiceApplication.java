package com.threeChickens.homeService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
@RestController
public class HomeServiceApplication {
	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+07:00"));
		SpringApplication.run(HomeServiceApplication.class, args);
	}


	@GetMapping("/")
	public String getHomePage() {
		return "<html>" +
				"<head>" +
				"<title>Home Service API</title>" +
				"<style>" +
				"body { font-family: Arial, sans-serif; text-align: center; background-color: #ffffff; padding: 50px; }" +
				"h1 { color: #333; margin-bottom: 70px; }" +
				"p { font-size: 18px; }" +
				"a { text-decoration: none; color: white; background-color: #007bff; padding: 10px 20px; border-radius: 5px; }" +
				"a:hover { background-color: #0056b3; }" +
				".container { background: white; padding: 20px; border-radius: 10px; display: inline-block; box-shadow: 0 0 10px rgba(0,0,0,0.1); }" +
				"</style>" +
				"</head>" +
				"<body>" +
				"<h1>Welcome to Home Service API</h1>" +
				"<p><a href='/swagger-ui/index.html'>Click here to go to Swagger UI</a></p>" +
				"</body>" +
				"</html>";
	}
}
