package com.experis.caritocomprarest.services;

import com.experis.caritocomprarest.data.Product;
import com.experis.caritocomprarest.repositories.ProductRepository;
import com.experis.caritocomprarest.web.DTOS.ProductDto;
import com.experis.caritocomprarest.web.DTOS.ProductPagedList;
import com.experis.caritocomprarest.web.mappers.ProductMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductPagedList getAllProductsByName(String name, PageRequest pageRequest) {
        log.debug("getting all products by name ");

        Page<Product> productPage = productRepository.findAllByNameContains(name,pageRequest);

        return getPagedListFromPage(productPage);
    }

    @Override
    public ProductPagedList getAllProductsByBrand(String brand, PageRequest pageRequest) {
        log.debug("getting all products by brand ");

        Page<Product>productPage = productRepository.findAllByBrand(brand,pageRequest);

        return getPagedListFromPage(productPage);
    }

    @Override
    public ProductPagedList getAllProductsByPriceRange(BigDecimal min, BigDecimal max, PageRequest pageRequest) {

        Page<Product> productPage = productRepository.findAllByPriceBetween(min, max,pageRequest);

        return getPagedListFromPage(productPage);
    }

    private ProductPagedList getPagedListFromPage(Page<Product> productPage){

        ProductPagedList productPagedList;
        List<ProductDto> productDtos = new ArrayList<>();

        productPage.getContent().forEach(product -> productDtos.add(productMapper.productToProductDto(product)));

        productPagedList = new ProductPagedList(
                productDtos,
                PageRequest.of(productPage.getPageable().getPageNumber(),
                        productPage.getPageable().getPageSize()),
                productPage.getTotalElements()
        );

        return productPagedList;
    }
}
