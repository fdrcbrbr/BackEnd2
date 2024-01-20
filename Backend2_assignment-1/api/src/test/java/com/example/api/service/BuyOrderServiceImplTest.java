package com.example.api.service;

import com.example.api.model.BuyOrder;
import com.example.api.model.Customer;
import com.example.api.model.Item;
import com.example.api.repository.BuyOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
class BuyOrderServiceImplTest {

    @MockBean
    private BuyOrderRepository mockRepository;

    @Autowired
    BuyOrderServiceImpl buyOrderService;

    @BeforeEach
    void setUp() {
        Customer customer1 = Customer.builder().name("Lennart Skoglund")
                .address("Stockholm")
                .username("lennart")
                .password("1234")
                .id(1L).build();
        Customer customer2 = Customer.builder().name("David Beckham")
                .address("Manchester")
                .username("david")
                .password("1234")
                .id(2L).build();

        Item item1 = Item.builder().id(1L).name("MacBook Pro").build();
        Item item2 = Item.builder().id(2L).name("iPad").build();
        Item item3 = Item.builder().id(3L).name("iPhone").build();

        BuyOrder buyOrder1 = BuyOrder.builder().id(1L).customer(customer1).items(List.of(item1, item2, item3)).build();
        BuyOrder buyOrder2 = BuyOrder.builder().id(2L).customer(customer2).items(List.of(item3)).build();

        when(mockRepository.findById(1L)).thenReturn(Optional.of(buyOrder1));
        when(mockRepository.findAll()).thenReturn(List.of(buyOrder1, buyOrder2));
        when(mockRepository.findAllByCustomerId(2L)).thenReturn(List.of(buyOrder2));

    }

    @Test
    @DisplayName("Get all orders")
    void getAllOrders() {
        List<BuyOrder> actual = List.of(
                BuyOrder.builder().id(1L).customer(Customer.builder().name("Lennart Skoglund")
                        .address("Stockholm")
                        .username("lennart")
                        .password("1234")
                        .id(1L).build()).items(List.of(
                        Item.builder().id(1L).name("MacBook Pro").build(),
                        Item.builder().id(2L).name("iPad").build(),
                        Item.builder().id(3L).name("iPhone").build()
                )).build(),
                BuyOrder.builder().id(2L).customer(Customer.builder().name("David Beckham")
                        .address("Manchester")
                        .username("david")
                        .password("1234")
                        .id(2L).build()).items(List.of(Item.builder().id(3L).name("iPhone").build())).build()
        );
        List<BuyOrder> notActual = List.of();
        List<BuyOrder> expected = mockRepository.findAll();
        assertThat(actual, is(expected));
        assertThat(notActual, is(not(expected)));
        assertThat(actual, hasSize(2));
        assertThat(actual.size(), is(2));
    }

    @Test
    @DisplayName("Get all orders for a specific customerid")
    void getOrdersByCustomerId() {
        long customerId = 2L;
        List<BuyOrder> actual = List.of(BuyOrder.builder().id(2L).customer(Customer.builder().name("David Beckham")
                .address("Manchester")
                .username("david")
                .password("1234")
                .id(2L).build()).items(List.of(Item.builder().id(3L).name("iPhone").build())).build());
        List<BuyOrder> expected = mockRepository.findAllByCustomerId(customerId);
        List<BuyOrder> notActual = List.of();

        assertThat(actual, is(expected));
        assertThat(notActual, is(not(expected)));
        assertThat(actual, hasSize(1));
        assertThat(actual.size(), is(1));
    }

}