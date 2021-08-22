package com.myapp.qrcodeapp.services;

import com.myapp.qrcodeapp.entities.Product;
import com.myapp.qrcodeapp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository repo;

    @Override
    public Iterable<Product> findAll() {
        return repo.findAll();
    }

    public List<Product> listAll(){
        return (List<Product>) repo.findAll();
    }

    @Override
    public void save(Product p) {
        repo.save(p);
    }

    @Override
    public Product get(Integer id) {
        Optional<Product> result = repo.findById(id);
        if(result.isPresent()){
            return result.get();
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        Long count = repo.countById(id);
        if(count == null || count ==0){
            System.out.println("Không tìm thấy thông tin sản phẩm với ID: "+id);
        }
        repo.deleteById(id);
    }

}
