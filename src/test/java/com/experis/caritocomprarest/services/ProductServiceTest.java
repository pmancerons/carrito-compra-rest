package com.experis.caritocomprarest.services;

import com.experis.caritocomprarest.data.Product;
import com.experis.caritocomprarest.repositories.ProductRepository;
import com.experis.caritocomprarest.web.mappers.ProductMapper;
import com.experis.caritocomprarest.web.mappers.ProductMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolverSupport;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    ProductService productService;

    @Mock
    ProductRepository productRepository;

    ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        productMapper = new ProductMapperImpl();
        productService = new ProductServiceImpl(productRepository,productMapper);
    }

    @Test
    void getAllProductsByName() {
        Mockito.when(productRepository.findAllByNameContains(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
                .thenReturn(Page.empty(Pageable.ofSize(1)));

        productService.getAllProductsByName("name",PageRequest.of(0, 10));

        Mockito.verify(productRepository,Mockito.times(1)).findAllByNameContains(ArgumentMatchers.anyString(), ArgumentMatchers.any());
    }

    @Test
    void getAllProductsByBrand() {
        Mockito.when(productRepository.findAllByBrand(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
                .thenReturn(Page.empty(Pageable.ofSize(1)));

        productService.getAllProductsByBrand("name",PageRequest.of(0, 10));

        Mockito.verify(productRepository,Mockito.times(1)).findAllByBrand(ArgumentMatchers.anyString(), ArgumentMatchers.any());
    }

    @Test
    void getAllProductsByPriceRange() {
        Mockito.when(productRepository.findAllByPriceBetween(ArgumentMatchers.any(),ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(Page.empty(Pageable.ofSize(1)));

        productService.getAllProductsByPriceRange(BigDecimal.valueOf(1l),BigDecimal.valueOf(1l),PageRequest.of(0, 10));

        Mockito.verify(productRepository,Mockito.times(1)).findAllByPriceBetween(ArgumentMatchers.any(),ArgumentMatchers.any(), ArgumentMatchers.any());
    }
}