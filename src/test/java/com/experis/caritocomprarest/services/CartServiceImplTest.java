package com.experis.caritocomprarest.services;

import com.experis.caritocomprarest.data.Product;
import com.experis.caritocomprarest.exceptions.NotAddedException;
import com.experis.caritocomprarest.repositories.ProductRepository;
import com.experis.caritocomprarest.web.DTOS.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.ArgumentMatchers;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    CartService cartService;

    @Mock
    ProductRepository productRepository;

    @BeforeEach
    void setUp(){
        cartService = new CartServiceImpl(productRepository);
    }

    @Test
    void addProductToCartOK() {
        Mockito.when(productRepository.getById(ArgumentMatchers.anyLong()))
            .thenReturn(Product.builder().stockQuantity(2).build());

        ProductDto productToAdd = ProductDto.builder().id(1l).build();

        cartService.addProductToCart(productToAdd);

        Mockito.verify(productRepository,Mockito.times(1)).getById(ArgumentMatchers.anyLong());
    }

    @Test
    void getProductsFromCartNotEnoughQuantity() {
        Mockito.when(productRepository.getById(ArgumentMatchers.anyLong()))
                .thenReturn(Product.builder().stockQuantity(1).build());

        ProductDto productToAdd = ProductDto.builder().id(1l).name("").build();

        cartService.addProductToCart(productToAdd);
        Exception exception = assertThrows(NotAddedException.class, () -> cartService.addProductToCart(productToAdd));

        Mockito.verify(productRepository,Mockito.times(2)).getById(ArgumentMatchers.anyLong());
        assertEquals("there is not enough quantity of the product ",exception.getMessage());
    }

    @Test
    void checkOutCart() {
        Mockito.when(productRepository.getById(ArgumentMatchers.anyLong()))
            .thenReturn(Product.builder().stockQuantity(2).stockQuantity(2).build());

        ProductDto productToAdd = ProductDto.builder().id(1l).name("").build();

        cartService.addProductToCart(productToAdd);
        cartService.addProductToCart(productToAdd);

        cartService.checkOutCart();

        Mockito.verify(productRepository,Mockito.times(4)).getById(ArgumentMatchers.anyLong());
        Mockito.verify(productRepository,Mockito.times(2)).saveAndFlush(ArgumentMatchers.any());
    }
}