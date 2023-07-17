package com.itonse.clotheslink.order.domain;

import com.itonse.clotheslink.user.domain.Customer;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private String address;
    private String phone;
    private String requests;
    private int amount;
    private String orderDateTime;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
