package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import demo.data.dal.BlogDataAccessRepository;

@SpringBootApplication
//@ComponentScan("demo.data") //to scan packages mentioned
//@EnableMongoRepositories("demo.data") //to activate MongoDB repositories
@EnableMongoAuditing
public class ShoppingBlogServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingBlogServiceApplication.class, args);
	}

}
