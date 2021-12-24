package com.ofir.coupons.config;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:logger.properties")
public class AppConfig {

	@Bean
	public File exceptionsLogFile(@Value("${exceptionsLogFilePath}") String path) {
		return new File(path);
	}
	
	@Bean
	public File operationsLogFile(@Value("${operationsLogFilePath}") String path) {
		return new File(path);
	}
	
}
