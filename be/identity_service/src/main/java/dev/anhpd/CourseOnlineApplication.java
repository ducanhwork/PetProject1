package dev.anhpd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "dev.anhpd")
public class CourseOnlineApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseOnlineApplication.class, args);
    }

}
