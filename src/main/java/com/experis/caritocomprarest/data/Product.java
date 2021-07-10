package com.experis.caritocomprarest.data;

import lombok.*;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(uniqueConstraints={@UniqueConstraint(columnNames ={"name","brand"})})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stockQuantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatusEnum productStatus;

    @Column(nullable = false)
    private Integer discount;

    public static String[] getFiledNames(){
        Field[] fields = Product.class.getDeclaredFields();
        List fieldNames= new ArrayList<String>();

        for(int i=0;i<fields.length;i++){
            if(!fields[i].getName().equals("id")) {
                fieldNames.add(fields[i].getName());
            }
        }
        return (String[]) fieldNames.toArray(new String[0]);
    }
}
