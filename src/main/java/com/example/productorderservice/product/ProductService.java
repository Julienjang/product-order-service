package com.example.productorderservice.product;

import org.springframework.stereotype.Component;

@Component
class ProductService {
    private final ProductPort productPort;

    ProductService(final ProductPort productPort) {
        this.productPort = productPort;
    }

    public void addProduct(final AddProductRequest request) {
        //addProudct 구현
        final Product product = new Product(request.name(), request.price(), request.discountPolicy());

        productPort.save(product);
    }
}
