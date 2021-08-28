package com.myapp.qrcodeapp;

import com.myapp.qrcodeapp.entities.Product;
import com.myapp.qrcodeapp.repositories.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository repo;

    @Test
    public void testAdd(){
        Product p = new Product();
        p.setName("Samsungg123");
        p.setPrice(168.9f);
        p.setQuantity(50);
        p.setStatus("active");

        Product savedProduct = repo.save(p);

        Assertions.assertThat(savedProduct).isNotNull();
        Assertions.assertThat(savedProduct.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAll(){
        Iterable<Product> products = repo.findAll();

        Assertions.assertThat(products).hasSizeGreaterThan(0);

        for (Product product : products) {
            System.out.println(product);
        }
    }

    @Test
    public void testUpdate(){
        Integer productID = 10;
        Optional<Product> optionalProduct = repo.findById(productID);
        Product p = optionalProduct.get();
        p.setName("Samsung");
        repo.save(p);

        Product updatedProduct = repo.findById(productID).get();
        Assertions.assertThat(updatedProduct.getName()).isEqualTo("Samsung");
    }

    @Test
    public void testGet(){
        Integer productID = 1;
        Optional<Product> optionalProduct = repo.findById(productID);

        Assertions.assertThat(optionalProduct).isPresent();
        System.out.println(optionalProduct.get());
    }

    @Test
    public void testDelete(){
        Integer productID = 17;
        repo.deleteById(productID);

        Optional<Product> optionalProduct = repo.findById(productID);
        Assertions.assertThat(optionalProduct).isNotPresent();
    }
}
