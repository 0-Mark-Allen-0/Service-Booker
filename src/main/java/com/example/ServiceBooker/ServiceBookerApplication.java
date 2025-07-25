package com.example.ServiceBooker;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServiceBookerApplication {

	public static void main(String[] args) {

		//-----------------------------------------------------------------------------------------
		//Dotenv Loader:

		Dotenv dotenv = Dotenv.load();

		dotenv.entries().
				forEach(dotenvEntry ->
						System.setProperty(dotenvEntry.getKey(), dotenvEntry.getValue())
				);

		//-----------------------------------------------------------------------------------------

		SpringApplication.run(ServiceBookerApplication.class, args);
	}

}
