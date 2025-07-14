package com.alura.literalura;

import com.alura.literalura.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.naming.Context;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {


	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(LiteraluraApplication.class, args);
		Principal principal = context.getBean(Principal.class);
		principal.muestraElMenu();


	}

	@Override
	public void run(String... args) throws Exception {

	}
}