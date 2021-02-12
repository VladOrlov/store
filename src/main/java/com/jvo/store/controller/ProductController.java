package com.jvo.store.controller;

import com.jvo.store.dto.ProductDto;
import com.jvo.store.dto.ProductDtoUpdate;
import com.jvo.store.exception.ResourceNotFoundException;
import com.jvo.store.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.function.Supplier;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Validated
@CrossOrigin
@RestController
@RequestMapping(value = "/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping(path = "/search/by-category")
    public ResponseEntity<List<ProductDto>> getAllProductsByCategory(@RequestParam(name = "id") String category) {
        return ResponseEntity.ok(productService.findAllByCategory(category));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getProduct(@PathVariable String id) {
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(getResourceNotFoundException(id));
    }

    @GetMapping(path = "/categories")
    public ResponseEntity<List<String>> getProductCategories() {
        return ResponseEntity.ok(productService.getProductCategories());
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductDto productDto, Errors errors) {
        return ResponseEntity.ok(productService.addProduct(productDto));
    }

    @PutMapping(path = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody ProductDto productDto) {
        return productService.updateProduct(id, productDto)
                .map(ResponseEntity::ok)
                .orElseThrow(getResourceNotFoundException(id));
    }

    @PatchMapping(path = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> patchProduct(@PathVariable String id, @RequestBody ProductDtoUpdate productDto) {
        return productService.partiallyUpdateProduct(id, productDto)
                .map(ResponseEntity::ok)
                .orElseThrow(getResourceNotFoundException(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    private Supplier<ResourceNotFoundException> getResourceNotFoundException(String id){
        return () -> new ResourceNotFoundException("Resource with id: " + id + " not found!");
    }
}
