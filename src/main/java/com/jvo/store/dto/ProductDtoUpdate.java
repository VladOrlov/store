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
public class ProductDtoUpdate {

    @NotBlank
    private String id;

    @NotBlank
    private String sku;

    @Length(min = 3, max = 120)
    private String name;

    @NotBlank
    private String description;

    @Positive
    private Integer amount;

    @Positive
    private BigDecimal price;

    @NotBlank
    private String category;

    private Boolean active;

    private Long version;

    @NotBlank
    private String imageUrl;
}
