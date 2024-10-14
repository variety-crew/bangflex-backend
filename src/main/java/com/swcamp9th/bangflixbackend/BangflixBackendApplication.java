package com.swcamp9th.bangflixbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling  // 스케줄링 활성화
public class BangflixBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BangflixBackendApplication.class, args);
    }

}
