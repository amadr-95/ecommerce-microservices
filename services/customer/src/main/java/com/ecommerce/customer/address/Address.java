package com.ecommerce.customer.address;

import com.ecommerce.customer.customer.Customer;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Address {
    @Id
    @SequenceGenerator(
            name = "address_sequence_id",
            sequenceName = "address_sequence_id",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "address_sequence_id",
            strategy = SEQUENCE
    )
    private Integer id;
    private String street;
    private int zipCode;
    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
