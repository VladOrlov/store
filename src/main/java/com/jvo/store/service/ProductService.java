package com.jvo.store.service;

import com.jvo.store.dto.ProductDto;
import com.jvo.store.dto.ProductDtoUpdate;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductDto> findAll();

    List<ProductDto> findAllByCategory(String category);

    Optional<ProductDto> findById(String id);

    ProductDto addProduct(ProductDto product);

    Optional<ProductDto> updateProduct(String id, ProductDto product);

    void deleteProduct(String id);

    Optional<ProductDto> partiallyUpdateProduct(String id, @NonNull ProductDtoUpdate productDtoUpdate);

    List<String> getProductCategories();

}
