package com.jvo.store.repository;

import com.jvo.store.dto.ProductCategory;
import com.jvo.store.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findAllByCategory(String category);

    List<Product> findAllByActiveIsTrueAndCategory(String category);

    List<ProductCategory> findDistinctByCategoryNotNull();

}