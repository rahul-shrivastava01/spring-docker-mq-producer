package com.local.spring_docker_mq_producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class SpringDockerMqProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDockerMqProducerApplication.class, args);
	}

}
