package org.classly.schoolstructureservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.classly")
public class SchoolStructureServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolStructureServiceApplication.class, args);
    }

}
