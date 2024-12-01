package com.ruokatracker.ruokatracker_api;

import org.springframework.boot.SpringApplication;

public class TestRuokatrackerApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(RuokatrackerApiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
