package net.hackathlon.hcluser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class HCLUserApp {

	public static void main(String[] args) {
		SpringApplication.run(HCLUserApp.class, args);
	}

}
