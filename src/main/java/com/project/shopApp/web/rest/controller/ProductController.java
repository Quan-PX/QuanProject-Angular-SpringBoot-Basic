package com.project.shopApp.web.rest.controller;

import com.project.shopApp.domain.Product;
import com.project.shopApp.service.ProductService;
import com.project.shopApp.web.rest.dto.ProductDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product")
    public ResponseEntity<?> getAllProduct(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("createdAt").descending());

        Page<Product> products = productService.getAllProduct(pageRequest);

        int totalPages = products.getTotalPages();

        List<Product> listProduct = products.getContent();

        return ResponseEntity.ok(listProduct);
    }

    @PostMapping("/product")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO productDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        Product newProduct = productService.createProduct(productDTO);
        return ResponseEntity.ok(newProduct);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") long id, @Valid @RequestBody ProductDTO productDTO) {
        Product productUpdate = productService.updateProduct(id, productDTO);

        return ResponseEntity.ok(productUpdate);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteproduct(@PathVariable("id") long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Delete success");
    }
}
