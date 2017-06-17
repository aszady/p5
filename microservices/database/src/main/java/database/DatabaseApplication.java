package database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DatabaseApplication {

	public static void main(String[] args) {
		DatabaseController dbController = new DatabaseController();
		dbController.prepareIndex();
		SpringApplication.run(DatabaseApplication.class, args);
	}
}
