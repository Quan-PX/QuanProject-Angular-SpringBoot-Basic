package com.project.shopApp.service;

import com.project.shopApp.domain.Product;
import com.project.shopApp.web.rest.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    Page<Product> getAllProduct(Pageable pageable);

    Product createProduct(ProductDTO productDTO);

    Product updateProduct(Long ProductId, ProductDTO productDTO);

    Product getProductById(Long id);

    void deleteProduct(Long id);

}
