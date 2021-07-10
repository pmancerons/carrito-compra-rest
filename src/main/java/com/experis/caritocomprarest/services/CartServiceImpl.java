package com.experis.caritocomprarest.services;

import com.experis.caritocomprarest.web.DTOS.Cart;
import com.experis.caritocomprarest.data.Product;
import com.experis.caritocomprarest.exceptions.NotAddedException;
import com.experis.caritocomprarest.repositories.ProductRepository;
import com.experis.caritocomprarest.web.DTOS.ProductDto;
import com.experis.caritocomprarest.web.mappers.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private Cart cart =  new Cart();

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public void addProductToCart(ProductDto product) {
        int productQuantity = productRepository.getById(product.getId()).getStockQuantity();

        int quantityOrdered = cart.getProducts().stream().filter(p -> p.getId().equals(product.getId())).mapToInt(p->1).sum();

        if(productQuantity - quantityOrdered > 0) {
            cart.addProduct(product);
            log.info(product.getName() + " has been added");
        }else{
            log.info(product.getName() + " couldn´t been added because there is not enough quantity");
            throw new NotAddedException("there is not enough quantity of the product " + product.getName());
        }
    }

    @Override
    public List<ProductDto> getProductsFromCart() {
        log.info("getting all products form cart");
        return cart.getProducts();
    }

    @Override
    public void emptyCart() {
        log.info("clearing cart");
        cart.clearProducts();
    }

    @Override
    public void checkOutCart() {
        log.info("checking out cart");
        cart.getProducts().forEach(productDto -> {
            Product dataBaseProduct = productRepository.getById(productDto.getId());
            dataBaseProduct.setStockQuantity(dataBaseProduct.getStockQuantity() - 1);
            productRepository.saveAndFlush(dataBaseProduct);
        });
        cart.clearProducts();
    }
}
