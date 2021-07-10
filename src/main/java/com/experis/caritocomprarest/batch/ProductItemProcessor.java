package com.experis.caritocomprarest.batch;

import com.experis.caritocomprarest.data.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class ProductItemProcessor implements ItemProcessor<Product, Product> {

    @Override
    public Product process(Product product) throws Exception {
        log.debug("parsing from product " + product.toString() );
        String noBlankName = product.getName() == null || product.getName().isEmpty() || product.getName().isBlank() ? null : product.getName();
        String noBlankBrand = product.getBrand() == null || product.getBrand().isEmpty() || product.getBrand().isBlank() ? null : product.getBrand();

        product.setName(noBlankName);
        product.setBrand(noBlankBrand);

        log.debug(" to: " + product.toString() );

        return product;
    }
}
