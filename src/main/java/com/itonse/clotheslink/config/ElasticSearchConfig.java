package com.itonse.clotheslink.config;

import com.itonse.clotheslink.product.repository.ProductDocumentRepository;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories(basePackageClasses = ProductDocumentRepository.class)
@Configuration
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {

    @Value("${es.hostAndPort}")
    private String hostAndPort;

    @Override
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration configuration = ClientConfiguration.builder()
                .connectedTo(hostAndPort).build();
        return RestClients.create(configuration).rest();
    }
}
