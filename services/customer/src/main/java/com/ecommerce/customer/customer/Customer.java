package com.ecommerce.customer.customer;

import com.ecommerce.customer.address.Address;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class Customer {
    @Id
    @SequenceGenerator(
            name = "customer_sequence",
            sequenceName = "customer_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "customer_sequence",
            strategy = SEQUENCE
    )
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    @OneToMany(mappedBy = "customer", cascade = {MERGE, REMOVE})
    @ToString.Exclude
    private Set<Address> addresses;
}
