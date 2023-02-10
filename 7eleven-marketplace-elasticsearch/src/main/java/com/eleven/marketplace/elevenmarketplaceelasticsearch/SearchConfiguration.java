package com.eleven.marketplace.elevenmarketplaceelasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

    @Configuration
    public class SearchConfiguration
    {
        @Bean
        public RestClient getRestClient() {
            RestClient restClient = RestClient.builder(
                    new HttpHost("localhost", 9200)).build();
            return restClient;
        }

        @Bean
        public ElasticsearchTransport getsearchTransport() {
            return new RestClientTransport(
                    getRestClient(), new JacksonJsonpMapper());
        }


        @Bean
        public ElasticsearchClient getsearchClient(){
            ElasticsearchClient client = new ElasticsearchClient(getsearchTransport());
            return client;
        }

    }

