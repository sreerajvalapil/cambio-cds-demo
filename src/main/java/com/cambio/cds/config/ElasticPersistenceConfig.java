package com.cambio.cds.config;

import com.cambio.cds.persistence.CdsModelRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
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
            throw new IllegalArgumentException("The elastic host should not include the protocol, remove it from the configuration or update the source code to support other prots than http.");
        }
        //final ClientConfiguration configuration = ClientConfiguration.create(elasticHost);

        ClientConfiguration.TerminalClientConfigurationBuilder builder =
            ClientConfiguration.builder()
                                .connectedTo(elasticHost)
                                .usingSsl()
                                .withBasicAuth("elastic", "clmWdiYcFcOIousKooRKSQIn") ;
        final ClientConfiguration clientConfiguration = builder.build();
        RestHighLevelClient client = RestClients.create(clientConfiguration)
                .rest();
        /*
        String host = "f276c08588794b199394628eba9abecd.us-east-1.aws.found.io";
        int port = 9243 ;
        final CredentialsProvider credentialsProvider =
                new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "clmWdiYcFcOIousKooRKSQIn"));
        RestClientBuilder builder = RestClient.builder(new HttpHost(host, port, "http"))
                .setHttpClientConfigCallback(httpClientBuilder ->
                        httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
        RestHighLevelClient client = new RestHighLevelClient(builder);
        */

        return client;
    }

    @Bean
    ElasticsearchRestTemplate elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }
}

