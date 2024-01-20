package com.example.api.service;

import com.example.api.error.CustomizedNotFoundException;
import com.example.api.model.BuyOrder;
import com.example.api.model.Customer;
import com.example.api.model.Item;
import com.example.api.repository.BuyOrderRepository;
import com.example.api.repository.CustomerRepository;
import com.example.api.repository.ItemRepository;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
class ItemServiceImplTest {

    @Autowired
    private ItemServiceImpl itemService;
    @MockBean
    private ItemRepository mockRepository;
    @MockBean
    private BuyOrderRepository mockBuyOrderRepository;
    @MockBean
    private CustomerRepository mockCustomerRepository;

    @BeforeEach
    void setUp() {
        Item item1 = Item.builder().id(1L).name("MacBook Pro").build();
        Item item2 = Item.builder().id(2L).name("iPad").build();
        Item item3 = Item.builder().id(3L).name("iPhone").build();

        Customer customer = Customer.builder().name("Lennart Skoglund")
                .address("Stockholm")
                .username("lennart")
                .password("1234")
                .id(1L).build();
        BuyOrder buyOrder = BuyOrder.builder().id(1L).customer(customer).items(List.of(item3)).build();

        when(mockRepository.findById(3L)).thenReturn(Optional.of(item3));
        when(mockRepository.findAll()).thenReturn(List.of(item1, item2, item3));
        when(mockRepository.findItemByName("iPhone")).thenReturn(item3);

        when(mockBuyOrderRepository.findById(1L)).thenReturn(Optional.of(buyOrder));
        when(mockCustomerRepository.findById(1L)).thenReturn(Optional.of(customer));
    }

    @Test
    @DisplayName("Get all items")
    void getAllItems() {
        List<Item> actual = List.of(
                Item.builder().id(1L).name("MacBook Pro").build(),
                Item.builder().id(2L).name("iPad").build(),
                Item.builder().id(3L).name("iPhone").build()
                );
        List<Item> notActual = List.of();
        List<Item> expected = itemService.getAllItems();

        assertThat(actual, is(expected));
        assertThat(notActual, is(not(expected)));
        assertThat(actual, hasSize(3));
        assertThat(actual.size(), is(3));

    }

    @Test
    @DisplayName("Get item by name")
    void getItemByName() throws CustomizedNotFoundException {
        String itemName = "iPhone";
        Item found = itemService.getItemByName(itemName);

        assertEquals(itemName, found.getName());
    }

}