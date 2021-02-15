package com.jvo.store.controller;

import com.jvo.store.domain.ProductDto;
import com.jvo.store.exception.ProductNotFoundException;
import com.jvo.store.service.ProductService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<ProductDto>> findAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findProductById(@PathVariable String id) {
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(getResourceNotFoundException(id));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewProduct(@Valid @RequestBody ProductDto productDto, Errors errors) {

        ProductDto product = productService.addProduct(productDto);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> patchProduct(@PathVariable String id, @RequestBody ProductDto productDto) {
        return productService.updateProduct(id, productDto)
                .map(ResponseEntity::ok)
                .orElseThrow(getResourceNotFoundException(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    private Supplier<ProductNotFoundException> getResourceNotFoundException(String id) {
        return () -> new ProductNotFoundException("Resource with id: " + id + " not found!");
    }
}
