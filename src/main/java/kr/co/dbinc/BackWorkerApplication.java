package kr.co.dbinc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class BackWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackWorkerApplication.class, args);
	}

}
