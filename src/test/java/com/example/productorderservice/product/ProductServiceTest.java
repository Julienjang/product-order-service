package com.example.productorderservice.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductServiceTest {


    private ProductService productService;
    private ProductPort productPort;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
        productPort = new ProductPortAdapter(productRepository);
        productService = new ProductService(productPort);
    }

    @Test
     void 상품등록() {
        final AddProductRequest request= 상품등록요청_생성();
        productService.addProduct(request);

    }

    private AddProductRequest 상품등록요청_생성() {
        final String name= "상품명";
        final int price= 1000;
        final DicountPolicy discountPolicy= DicountPolicy.NONE;
        return new AddProductRequest(name, price, discountPolicy);
    }


}