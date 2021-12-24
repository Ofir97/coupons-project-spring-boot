package com.ofir.coupons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.ofir.coupons.utils.Test;

@SpringBootApplication
@EnableScheduling
public class Program {
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Program.class);
		context.getBean(Test.class).testAll();	
	}
}
