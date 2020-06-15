package com.cambio.cds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.allegro.tech.embeddedelasticsearch.EmbeddedElastic;
import pl.allegro.tech.embeddedelasticsearch.PopularProperties;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class CambioCdsDemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(CambioCdsDemoApplication.class, args);
        //startEmbeddedServer();
    }

    private static void startEmbeddedServer() {
        try {
            EmbeddedElastic.builder()
                    .withElasticVersion("6.8.3")
                    .withSetting(PopularProperties.HTTP_PORT, 31320)
                    .withSetting(PopularProperties.CLUSTER_NAME, "elasticsearch-cds")
                    .withStartTimeout(60, TimeUnit.SECONDS)
                    .build()
                    .start()
                    .deleteIndices();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
