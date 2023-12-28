package com.sparta.missionreport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MissionReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(MissionReportApplication.class, args);
    }

}
