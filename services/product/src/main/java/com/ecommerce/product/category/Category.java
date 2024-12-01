package com.ecommerce.product.category;

import com.ecommerce.product.product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Category {
    @Id
    @SequenceGenerator(
            name = "category_sequence",
            sequenceName = "category_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "category_sequence",
            strategy = SEQUENCE
    )
    private Integer id;
    private String category;
    @OneToMany(mappedBy = "category", cascade = {MERGE, REMOVE}) //when updating or removing a category the changes will be reflected in Product entity
    @ToString.Exclude
    private List<Product> products;

}
