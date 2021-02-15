package com.jvo.store.service;

import com.jvo.store.domain.ProductDto;
import com.jvo.store.exception.ProductNotFoundException;
import com.jvo.store.domain.Product;
import com.jvo.store.repository.ProductRepository;
import com.jvo.store.utils.EntityMapper;
import lombok.NonNull;
import lombok.var;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final EntityMapper entityMapper;

    public ProductServiceImpl(ProductRepository productRepository, EntityMapper entityMapper) {
        this.productRepository = productRepository;
        this.entityMapper = entityMapper;
    }

    public List<ProductDto> findAll() {
        return productRepository.findAll().stream()
                .map(entityMapper::convertToDto)
                .collect(toList());
    }

    @Override
    public Optional<ProductDto> findById(String id) {
        return productRepository.findById(id)
                .map(entityMapper::convertToDto);
    }

    @Override
    public ProductDto addProduct(ProductDto product) {
        return saveProduct(product);
    }

    private ProductDto saveProduct(ProductDto product) {
        Product entity = entityMapper.convertToEntity(product);
        return entityMapper.convertToDto(productRepository.save(entity));
    }

    @Override
    public Optional<ProductDto> updateProduct(String id, @NonNull ProductDto productDtoUpdate) {
        return productRepository.findById(id)
                .map(entity -> entityMapper.updateEntityValues(entity, productDtoUpdate))
                .map(productRepository::save)
                .map(entityMapper::convertToDto);
    }

    @Override
    public void deleteProduct(String id) {
        var entity = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Resource with id: " + id + " not found!"));
        productRepository.delete(entity);
    }
}