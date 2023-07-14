package com.myProject.reggie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@ServletComponentScan
public class ReggieApplication {
	public static void main(String[] args) {
		SpringApplication.run(ReggieApplication.class, args);
		log.info("Project booted!");
	}
}
