package com.jenny.prestamos_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class PruebaTecnicaPrestamosApplication {

    public static void main(String[] args) {
        SpringApplication.run(PruebaTecnicaPrestamosApplication.class, args);
    }

}
