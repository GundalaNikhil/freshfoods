package com.freshfoods.Service;

import com.freshfoods.Entity.Product;
import com.freshfoods.ExceptionHandling.InvalidDataException;
import com.freshfoods.ExceptionHandling.ResourceNotFoundException;
import com.freshfoods.Interface.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        validateProduct(product);
        product.setActive(true);
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStockQuantity(product.getStockQuantity());

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setActive(false);
        productRepository.save(product);
    }

    private void validateProduct(Product product) {
        if (product.getPrice() <= 0) {
            throw new InvalidDataException("Price must be greater than 0");
        }
        if (product.getStockQuantity() < 0) {
            throw new InvalidDataException("Stock quantity cannot be negative");
        }
    }
}
