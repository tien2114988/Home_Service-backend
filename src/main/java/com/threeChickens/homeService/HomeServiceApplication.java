package com.threeChickens.homeService;

import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Home Service")
public class HomeServiceApplication {
	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+07:00"));
		SpringApplication.run(HomeServiceApplication.class, args);
	}


	@GetMapping("/")
	public String getHomePage() {
		return """
  <!DOCTYPE html>
  <html lang="en">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Page Title</title>
    <style>
      body {
        margin: 0;
        font-family: Arial, sans-serif;
      }
      div[background] {
        background-image: url('https://png.pngtree.com/thumb_back/fh260/background/20220523/pngtree-cleaning-service-flat-background-with-group-of-young-women-in-uniform-image_1391520.jpg');
        background-size: cover;
        background-repeat: no-repeat;
        background-position: center;
        height: 100vh;
        width: 100vw;
        display: flex;
        align-items: center;
        justify-content: center;
      }
      .content {
        background: white;
        padding: 20px;
        border-radius: 10px;
        box-shadow: 0 0 20px rgba(0, 0, 0, 0.3);
        text-align: center;
      }
      h1 {
        color: #333;
        margin-bottom: 50px;
      }
      a {
        text-decoration: none;
        color: white;
        background-color: #007bff;
        padding: 10px 20px;
        border-radius: 5px;
        box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.2);
        transition: background-color 0.3s ease;
      }
      a:hover {
        background-color: #0056b3;
      }
    </style>
  </head>
  <body>
    <div background>
      <div class="content">
        <h1>Welcome to Home Service API</h1>
        <p><a href="/swagger-ui/index.html">Click here to go to Swagger UI</a></p>
      </div>
    </div>
  </body>
  </html>
  """;

	}
}
