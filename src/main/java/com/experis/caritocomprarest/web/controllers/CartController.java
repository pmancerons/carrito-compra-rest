package com.experis.caritocomprarest.web.controllers;

import com.experis.caritocomprarest.services.CartService;
import com.experis.caritocomprarest.web.DTOS.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/cart")
@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/addProduct")
    @ResponseStatus(HttpStatus.CREATED)
    public void addProductToCart(@RequestBody ProductDto productDto){
        cartService.addProductToCart(productDto);
    }

    @GetMapping
    public List<ProductDto> getProductsFromCart(){
        return cartService.getProductsFromCart();
    }

    @DeleteMapping("/clear")
    @ResponseStatus(HttpStatus.OK)
    public void clearCart(){
        cartService.emptyCart();
    }

    @PostMapping("/checkout")
    public void checkoutCart(){
        cartService.checkOutCart();
    }
}
