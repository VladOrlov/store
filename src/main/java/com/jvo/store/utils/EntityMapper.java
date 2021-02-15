package com.jvo.store.utils;

import com.jvo.store.domain.ProductDto;
import com.jvo.store.domain.Product;
import lombok.var;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EntityMapper {

    private ModelMapper modelMapper;

    @PostConstruct
    public void init(){
        var modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setSkipNullEnabled(true);

        this.modelMapper = modelMapper;
    }

    public Product convertToEntity(ProductDto dto){
        return modelMapper.map(dto, Product.class);
    }

    public ProductDto convertToDto(Product entity){
        return modelMapper.map(entity, ProductDto.class);
    }

    public Product updateEntityValues(Product entity, ProductDto dto){
        modelMapper.map(dto, entity);
        return entity;
    }
}
