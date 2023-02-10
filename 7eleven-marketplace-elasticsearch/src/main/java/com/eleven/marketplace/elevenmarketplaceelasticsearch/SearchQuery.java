package com.eleven.marketplace.elevenmarketplaceelasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import org.apache.lucene.queryparser.flexible.core.builders.QueryBuilder;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class SearchQuery {

    private final ElasticsearchClient elasticsearchClient;

    private final String indexName = "products";

    public SearchQuery(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }


    public String createOrUpdateDocument(Product product) throws IOException {

        IndexResponse response = elasticsearchClient.index(i -> i
                .index(indexName)
                .id(product.getId())
                .document(product)
        );
        if(response.result().name().equals("Created")){
            return new StringBuilder("Document has been successfully created.").toString();
        }else if(response.result().name().equals("Updated")){
            return new StringBuilder("Document has been successfully updated.").toString();
        }
        return new StringBuilder("Error while performing the operation.").toString();
    }

    public Product getDocumentById(String productId) throws IOException{
        Product product = null;
        GetResponse<Product> response = elasticsearchClient.get(g -> g
                        .index(indexName)
                        .id(productId),
                Product.class
        );

        if (response.found()) {
            product = response.source();
            System.out.println("Product name " + product.getName());
        } else {
            System.out.println ("Product not found");
        }

        return product;
    }

    public String deleteDocumentById(String productId) throws IOException {

        DeleteRequest request = DeleteRequest.of(d -> d.index(indexName).id(productId));

        DeleteResponse deleteResponse = elasticsearchClient.delete(request);
        if (Objects.nonNull(deleteResponse.result()) && !deleteResponse.result().name().equals("NotFound")) {
            return new StringBuilder("Product with id " + deleteResponse.id() + " has been deleted.").toString();
        }
        System.out.println("Product not found");
        return new StringBuilder("Product with id " + deleteResponse.id()+" does not exist.").toString();

    }

    public  List<Product> searchAllDocuments() throws IOException {

        SearchRequest searchRequest =  SearchRequest.of(s -> s.index(indexName));
        SearchResponse searchResponse =  elasticsearchClient.search(searchRequest, Product.class);
        List<Hit> hits = searchResponse.hits().hits();
        List<Product> products = new ArrayList<>();
        for(Hit object : hits){

            System.out.print(((Product) object.source()));
            products.add((Product) object.source());

        }
        return products;
    }

//    public List<Product> processSearch(final String query) {
//        log.info("Search with query {}", query);
//
//        // 1. Create query on multiple fields enabling fuzzy search
//        MultiMatchQueryBuilder queryBuilder =
//                 QueryBuilders
//                        .multiMatchQuery(query, "name", "description")
//                        .fuzziness(Fuzziness.AUTO);
//
//        SearchRequest searchRequest =  SearchRequest.of(s -> s.);
//        SearchResponse searchResponse =  elasticsearchClient.search(queryBuilder, Product.class);
//        List<Hit> hits = searchResponse.hits().hits();
//        List<Product> products = new ArrayList<>();
//        for(Hit object : hits){
//
//            System.out.print(((Product) object.source()));
//            products.add((Product) object.source());
//
//        }
//        return products;
//    }
}
