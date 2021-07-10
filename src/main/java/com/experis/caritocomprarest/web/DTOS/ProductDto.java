package com.experis.caritocomprarest.web.DTOS;

import com.experis.caritocomprarest.data.ProductStatusEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductDto {

    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private Integer stockQuantity;
    private String productStatus;
    private Integer discount;
}
