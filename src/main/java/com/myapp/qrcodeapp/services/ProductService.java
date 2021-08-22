package com.myapp.qrcodeapp.services;

import com.myapp.qrcodeapp.entities.Product;
import com.myapp.qrcodeapp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    public Iterable<Product> findAll();
    public List<Product> listAll();

    public void save(Product p);
    public Product get(Integer id);
    public void delete(Integer id);

}
