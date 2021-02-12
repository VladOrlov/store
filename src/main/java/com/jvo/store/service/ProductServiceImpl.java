package com.jvo.store.service;

import com.jvo.store.dto.ProductCategory;
import com.jvo.store.dto.ProductDto;
import com.jvo.store.dto.ProductDtoUpdate;
import com.jvo.store.exception.ResourceNotFoundException;
import com.jvo.store.model.Product;
import com.jvo.store.repository.ProductRepository;
import com.jvo.store.utils.ObjectMapper;
import lombok.NonNull;
import lombok.var;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    public ProductServiceImpl(ProductRepository productRepository, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }

    public List<ProductDto> findAll() {
        return productRepository.findAll().stream()
                .map(objectMapper::toDto)
                .collect(toList());
    }

    @Override
    public List<ProductDto> findAllByCategory(String category) {
        return productRepository.findAllByCategory(category).stream()
                .map(objectMapper::toDto)
                .collect(toList());
    }

    @Override
    public Optional<ProductDto> findById(String id) {
        return productRepository.findById(id)
                .map(objectMapper::toDto);
    }

    @Override
    public ProductDto addProduct(ProductDto product) {
        return saveProduct(product);
    }

    private ProductDto saveProduct(ProductDto product) {
        Product entity = objectMapper.toEntity(product);
        return objectMapper.toDto(productRepository.save(entity));
    }

    @Override
    public Optional<ProductDto> updateProduct(String id, @NonNull ProductDto productDto) {
        return productRepository.findById(id)
                .map(entity -> objectMapper.updateEntity(entity, productDto))
                .map(productRepository::save)
                .map(objectMapper::toDto);
    }

    @Override
    public Optional<ProductDto> partiallyUpdateProduct(String id, @NonNull ProductDtoUpdate productDtoUpdate) {
        return productRepository.findById(id)
                .map(entity -> objectMapper.updateEntity(entity, productDtoUpdate))
                .map(productRepository::save)
                .map(objectMapper::toDto);
    }

    @Override
    public List<String> getProductCategories() {
        return productRepository.findDistinctByCategoryNotNull().stream()
                .map(ProductCategory::getCategory)
                .distinct()
                .collect(toList());
    }

    @Override
    public void deleteProduct(String id) {
        var entity = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource with id: " + id + " not found!"));
        productRepository.delete(entity);
    }
}