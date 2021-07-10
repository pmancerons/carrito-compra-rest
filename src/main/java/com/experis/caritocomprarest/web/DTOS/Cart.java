package com.experis.caritocomprarest.web.DTOS;

import com.experis.caritocomprarest.data.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Cart {
    private List<ProductDto> products = new ArrayList<>();

    public void addProduct(ProductDto product){
        this.products.add(product);
    }

    public void clearProducts(){
        this.products = new ArrayList<>();
    }
}
