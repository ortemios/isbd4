package org.isbd.part4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories("org.isbd.part4.repository")
public class IsbdApplication {

    public static void main(String[] args) {
        SpringApplication.run(IsbdApplication.class, args);
    }

}
