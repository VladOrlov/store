package com.jvo.store.service;

import com.jvo.store.domain.ProductDto;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductDto> findAll();

    Optional<ProductDto> findById(String id);

    ProductDto addProduct(ProductDto product);

    Optional<ProductDto> updateProduct(String id, @NonNull ProductDto productDtoUpdate);

    void deleteProduct(String id);

}