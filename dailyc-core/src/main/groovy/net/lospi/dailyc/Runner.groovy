package net.lospi.dailyc

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class Runner {
    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args)
    }
}
