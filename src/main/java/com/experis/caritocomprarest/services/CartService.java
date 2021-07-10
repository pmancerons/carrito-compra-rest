package com.experis.caritocomprarest.services;

import com.experis.caritocomprarest.data.Product;
import com.experis.caritocomprarest.web.DTOS.ProductDto;

import java.util.List;

public interface CartService {
    void addProductToCart(ProductDto product);
    List<ProductDto> getProductsFromCart();
    void emptyCart();
    void checkOutCart();
}
