package com.krakedev.apijdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = "com.krakedev")
public class ApijdbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApijdbcApplication.class, args);
	}

}
