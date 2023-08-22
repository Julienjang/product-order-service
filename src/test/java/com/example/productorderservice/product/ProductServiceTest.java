package com.example.productorderservice.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

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
        final String name= "상품명";
        final int price= 1000;
        final DicountPolicy discountPolicy= DicountPolicy.NONE;
        final AddProductRequest request= new AddProductRequest(name, price, discountPolicy);

        productService.addProduct(request);
    }
    private class ProductService {
        private final ProductPort productPort;

        private ProductService(ProductPort productPort) {
            this.productPort = productPort;
        }

        public void addProduct(final AddProductRequest request){
            //addProudct 구현
           final Product product = new Product(request.name(), request.price(), request.discountPolicy());

           productPort.save(product);
        }
    }


    private record AddProductRequest(String name, int price, DicountPolicy discountPolicy) {
        private AddProductRequest {
            Assert.hasText(name, "상품명은 필수입니다.");
            Assert.isTrue(price > 0, "상품 가격은 0보다 커");
            Assert.notNull(discountPolicy, "할인 정책은 필수 입니다.");
        }
        }




    private enum DicountPolicy {
        NONE
    }

    private class Product {
        private Long id;
        private final String name;
        private final int price;
        private final DicountPolicy discountPolicy;


        public Product(final String name, final int price, DicountPolicy discountPolicy) {
            Assert.hasText(name, "상품명은 필수입니다.");
            Assert.isTrue(price > 0, "상품 가격은 0보다 커");
            Assert.notNull(discountPolicy, "할인 정책은 필수 입니다.");
            this.name = name;
            this.price = price;
            this.discountPolicy = discountPolicy;
        }

        public Long getId() {
            return id;
        }

        public void assignId(final Long id) {
            this.id= id;
        }
    }

    private interface ProductPort {
        void save(final Product product);
    }

    private class ProductPortAdapter implements ProductPort {
        private final ProductRepository ProductRepository;

        private ProductPortAdapter(final ProductRepository productRepository) {
            this.ProductRepository = productRepository;
        }

        @Override
        public void save(final Product product){
            ProductRepository.save(product);
        }
    }

    private class ProductRepository {
        private Long sequence= 0L;
        private Map<Long, Product> persistence = new HashMap<>();

        public void save(final Product product) {
            product.assignId(++sequence);
            persistence.put(product.getId(),product);
        }
    }
}