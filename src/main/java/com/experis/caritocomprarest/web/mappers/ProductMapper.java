package com.experis.caritocomprarest.web.mappers;

import com.experis.caritocomprarest.data.Product;
import com.experis.caritocomprarest.web.DTOS.ProductDto;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {
    ProductDto productToProductDto(Product product);
    Product productDtoToProduct(ProductDto productDto);
}
