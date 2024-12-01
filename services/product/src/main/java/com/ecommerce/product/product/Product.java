package com.ecommerce.product.product;

import com.ecommerce.product.category.Category;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Product {
    @Id
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "product_sequence",
            strategy = SEQUENCE
    )
    private Integer id;
    private String name;
    private String description;
    @Column(name = "available_quantity")
    private int quantity;
    private BigDecimal price;
    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private Category category;
}
