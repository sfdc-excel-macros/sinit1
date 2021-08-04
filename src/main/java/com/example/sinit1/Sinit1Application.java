package com.example.sinit1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@ServletComponentScan
@SpringBootApplication
public class Sinit1Application {

	public static void main(String[] args) {
		SpringApplication.run(Sinit1Application.class, args);
	}

}
