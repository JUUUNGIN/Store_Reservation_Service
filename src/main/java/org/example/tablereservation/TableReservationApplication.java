package org.example.tablereservation;

import lombok.ToString;
import org.example.tablereservation.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class TableReservationApplication {

    public static void main(String[] args) {
        SpringApplication.run(TableReservationApplication.class, args);
    }

}
