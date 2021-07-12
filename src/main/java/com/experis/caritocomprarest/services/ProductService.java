package com.experis.caritocomprarest.services;

import com.experis.caritocomprarest.web.DTOS.ProductPagedList;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;

public interface ProductService {
    ProductPagedList getAllProductsByName(String name, PageRequest pageRequest);

    ProductPagedList getAllProductsByBrand(String brand, PageRequest pageRequest);

    ProductPagedList getAllProductsByPriceRange(BigDecimal min, BigDecimal max, PageRequest pageRequest);

}
