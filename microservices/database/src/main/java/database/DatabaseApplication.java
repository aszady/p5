package database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DatabaseApplication {

	public static void main(String[] args) {
		DatabaseController dbController = new DatabaseController();
		dbController.prepareIndex();
		SpringApplication.run(DatabaseApplication.class, args);
	}
}
