package com.jumiapay.users.audit.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = "com.jumiapay.users.audit")
@EnableMongoRepositories(basePackages = "com.jumiapay.users.audit")
public class UsersAuditApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersAuditApplication.class, args);
	}

}
