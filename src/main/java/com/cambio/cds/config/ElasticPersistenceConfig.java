package com.cambio.cds.config;

import com.cambio.cds.persistence.CdsModelRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Slf4j
@Configuration
@EnableElasticsearchRepositories(basePackageClasses = CdsModelRepository.class)
public class ElasticPersistenceConfig {

    @Value("${cds.model.search.elastic.address}")
    private String elasticHost;

    @Bean
    RestHighLevelClient elasticsearchClient() {
        log.info("using elastic search host {}", elasticHost);
        if (elasticHost.toLowerCase().startsWith("http")) {
            throw new IllegalArgumentException("The elastic host should not include the protocol, " +
                    "remove it from the configuration or update the source code to support other prots than http.");
        }
        
        ClientConfiguration.TerminalClientConfigurationBuilder builder =
            ClientConfiguration.builder()
                                .connectedTo(elasticHost)
                                .usingSsl()
                                .withBasicAuth("elastic", "clmWdiYcFcOIousKooRKSQIn") ;
        final ClientConfiguration clientConfiguration = builder.build();
        RestHighLevelClient client = RestClients.create(clientConfiguration)
                .rest();

        return client;
    }

    @Bean
    ElasticsearchRestTemplate elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }
}

