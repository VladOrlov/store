package com.jvo.store.utils;

import com.jvo.store.dto.ProductDto;
import com.jvo.store.dto.ProductDtoUpdate;
import com.jvo.store.model.Product;
import lombok.var;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ObjectMapper {

    private ModelMapper modelMapper;

    @PostConstruct
    public void init(){
        var modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setSkipNullEnabled(true);

        this.modelMapper = modelMapper;
    }

    public Product toEntity(ProductDto dto){
        return modelMapper.map(dto, Product.class);
    }

    public ProductDto toDto(Product entity){
        return modelMapper.map(entity, ProductDto.class);
    }

    public Product updateEntity(Product entity, ProductDto dto){
        modelMapper.map(dto, entity);
        return entity;
    }

    public Product updateEntity(Product entity, ProductDtoUpdate dto){
        modelMapper.map(dto, entity);
        return entity;
    }
}
