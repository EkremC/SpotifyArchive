package com.ekremc.spotifyarchive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpotifyArchiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpotifyArchiveApplication.class, args);
    }

}
