package com.experis.caritocomprarest.web.controllers;

import com.experis.caritocomprarest.services.ProductService;
import com.experis.caritocomprarest.web.DTOS.ProductPagedList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RequestMapping("/api/v1/product")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 5;

    private final ProductService productService;


    @GetMapping("/name")
    public ResponseEntity<ProductPagedList> listProductsByName(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                      @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                      @RequestParam(value = "productName", required = true) String productName){

        pageNumber = pageNumber == null || pageNumber < 0 ? DEFAULT_PAGE_NUMBER: pageNumber;
        pageSize = pageSize == null || pageSize < 1 ? DEFAULT_PAGE_SIZE: pageSize;

        ProductPagedList productPagedList = productService.getAllProductsByName(productName, PageRequest.of(pageNumber, pageSize));

        return new ResponseEntity<>(productPagedList, HttpStatus.OK);
    }

    @GetMapping("/brand")
    public ResponseEntity<ProductPagedList> listProductsByBrand(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                         @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                         @RequestParam(value = "brandName", required = true) String brandName){

        pageNumber = pageNumber == null || pageNumber < 0 ? DEFAULT_PAGE_NUMBER: pageNumber;
        pageSize = pageSize == null || pageSize < 1 ? DEFAULT_PAGE_SIZE: pageSize;

        ProductPagedList productPagedList = productService.getAllProductsByBrand(brandName, PageRequest.of(pageNumber, pageSize));

        return new ResponseEntity<>(productPagedList, HttpStatus.OK);
    }

    @GetMapping("/price")
    public ResponseEntity<ProductPagedList> listProductsByPrice(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                         @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                         @RequestParam(value = "minPrice", required = true) BigDecimal minPrice,
                                                         @RequestParam(value = "maxPrice", required = true) BigDecimal maxPrice){

        pageNumber = pageNumber == null || pageNumber < 0 ? DEFAULT_PAGE_NUMBER: pageNumber;
        pageSize = pageSize == null || pageSize < 1 ? DEFAULT_PAGE_SIZE: pageSize;

        ProductPagedList productPagedList = productService.getAllProductsByPriceRange(minPrice, maxPrice, PageRequest.of(pageNumber, pageSize));

        return new ResponseEntity<>(productPagedList, HttpStatus.OK);
    }
}
