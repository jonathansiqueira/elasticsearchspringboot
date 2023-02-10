package com.eleven.marketplace.elevenmarketplaceelasticsearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
@RestController
public class SearchController {

    private final SearchQuery searchQuery;

    public SearchController(SearchQuery searchQuery) {
        this.searchQuery = searchQuery;
    }

    @PostMapping("/createOrUpdateItem")
    public ResponseEntity<Object> createOrUpdateDocument(@RequestBody Product product) throws IOException {
        String response = searchQuery.createOrUpdateDocument(product);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getItem")
    public ResponseEntity<Object> getDocumentById(@RequestParam String productId) throws IOException {
        Product product =  searchQuery.getDocumentById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/deleteItem")
    public ResponseEntity<Object> deleteDocumentById(@RequestParam String productId) throws IOException {
        String response =  searchQuery.deleteDocumentById(productId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/searchItem")
    public ResponseEntity<Object> searchAllDocument() throws IOException {
        List<Product> products = searchQuery.searchAllDocuments();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
