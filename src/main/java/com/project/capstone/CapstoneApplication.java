package com.project.capstone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class CapstoneApplication {

	public static void main(String[] args) {
		//		SpringApplication.run(CapstoneApplication.class, args);

		SpringApplication application = new SpringApplication(CapstoneApplication.class);
		application.addListeners(new ApplicationPidFileWriter());
		application.run(args);
	}

}
