package com.belajar.middleware_iso8583;

import org.jpos.q2.Q2;
import org.jpos.q2.iso.QMUX;
import org.jpos.util.NameRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootMiddlewareIso8583Application {

	public static void main(String[] args) {
		Q2 q2 = new Q2();
		q2.start();
		SpringApplication.run(SpringBootMiddlewareIso8583Application.class, args);
	}

	@Bean
	public QMUX qmux() throws NameRegistrar.NotFoundException {
		return (QMUX) NameRegistrar.get("mux.belajar");
	}

}
