package com.ecommerce.order.orderline;

import com.ecommerce.order.order.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class OrderLine {

    @Id
    @SequenceGenerator(
            name = "orderline_sequence",
            sequenceName = "orderline_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "orderline_sequence"
    )
    private Integer id;
    private Integer quantity;
    private Integer productId;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
