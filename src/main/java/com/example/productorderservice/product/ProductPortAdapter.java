package com.example.productorderservice.product;

import org.springframework.stereotype.Component;

@Component
class ProductPortAdapter implements ProductPort {
    private final ProductRepository ProductRepository;

    ProductPortAdapter(final ProductRepository productRepository) {
        this.ProductRepository = productRepository;
    }

    @Override
    public void save(final Product product) {
        ProductRepository.save(product);
    }
}
