package com.jvo.store.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDto {

    private String id;

    private String sku;

    @NotNull
    @NotBlank
    @Length(min = 3, max = 120)
    private String name;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @Positive
    private Integer amount;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    @NotBlank
    private String category;

    private boolean active;

    private Long version;

    private String imageUrl;
}
