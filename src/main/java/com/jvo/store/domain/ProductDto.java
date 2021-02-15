package com.jvo.store.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDto {

    private String id;

    private String name;

    private String description;

    private Integer amountInStock;

    private BigDecimal unitPrice;

    private String imageUrl;

    private Long version;

}
