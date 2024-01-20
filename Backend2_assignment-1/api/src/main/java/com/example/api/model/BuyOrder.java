package com.example.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data       // Lombok (innehåller @NoArgsConstructor)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuyOrder {

    @Id
    @SequenceGenerator(
            name = "buyOrder_sequence",
            sequenceName = "buyOrder_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "buyOrder_sequence"
    )
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Customer customer;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="buy_order_item_map",
            joinColumns = @JoinColumn(
                    referencedColumnName ="id")
            ,
            inverseJoinColumns = @JoinColumn(
                    referencedColumnName ="id"
            )
    )
    private List<Item> items;

}
