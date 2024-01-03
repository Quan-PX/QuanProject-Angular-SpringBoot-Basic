package com.project.shopApp.service;

import com.project.shopApp.domain.Category;
import com.project.shopApp.domain.Product;
import com.project.shopApp.repository.CategoryRepository;
import com.project.shopApp.repository.ProductRepository;
import com.project.shopApp.web.rest.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService implements IProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<Product> getAllProduct(Pageable pageable) {
        return null;
    }

    @Override
    public Product createProduct(ProductDTO productDTO) {
        Category existingCategory = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(
                () -> new RuntimeException("Can not find Category Id")
        );

        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .decription(productDTO.getDecription())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .category(existingCategory)
                .build();

        return productRepository.save(newProduct);
    }

    @Override
    public Product updateProduct(Long productId, ProductDTO productDTO) {
        Product newProduct = getProductById(productId);
        if(newProduct != null){
            Category existingCategory = categoryRepository.findById(productDTO.getCategoryId())
                            .orElseThrow(() -> new RuntimeException("can nnot find CategoryId"));

            newProduct.setName(productDTO.getName());
            newProduct.setThumbnail(productDTO.getThumbnail());
            newProduct.setDecription(productDTO.getDecription());
            newProduct.setPrice(productDTO.getPrice());

            return productRepository.save(newProduct);
        }

        return null;
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("product not found"));

    }

    @Override
    public void deleteProduct(Long id) {
        Optional<Product> deleteProduct = productRepository.findById(id);
        deleteProduct.ifPresent(product -> productRepository.delete(product));
    }
}
