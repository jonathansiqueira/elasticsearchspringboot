package com.eleven.marketplace.elevenmarketplaceelasticsearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class UIController {

    private final SearchQuery elasticSearchQuery;

    public UIController(SearchQuery elasticSearchQuery) {
        this.elasticSearchQuery = elasticSearchQuery;
    }

    @GetMapping("/")
    public String viewHomePage(Model model) throws IOException {
        model.addAttribute("listProductDocuments",elasticSearchQuery.searchAllDocuments());
        return "index";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute("product") Product product, Model model) throws IOException {
        elasticSearchQuery.createOrUpdateDocument(product);
        model.addAttribute("listProductDocuments",elasticSearchQuery.searchAllDocuments());
        return "index";
        //return "redirect:/";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") String id, Model model) throws IOException {

        Product product = elasticSearchQuery.getDocumentById(id);
        model.addAttribute("product", product);
        return "updateProductDocument";
    }

    @GetMapping("/showNewProductForm")
    public String showNewEmployeeForm(Model model) {
        // create model attribute to bind form data
        Product product = new Product();
        model.addAttribute("product", product);
        return "newProductDocument";
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable(value = "id") String id) throws IOException {

        this.elasticSearchQuery.deleteDocumentById(id);
        return "redirect:/";
    }
}