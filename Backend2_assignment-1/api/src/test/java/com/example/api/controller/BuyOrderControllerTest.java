package com.example.api.controller;

import com.example.api.model.BuyOrder;
import com.example.api.model.Customer;
import com.example.api.model.Item;
import com.example.api.repository.BuyOrderRepository;
import com.example.api.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class BuyOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BuyOrderRepository mockRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @MockBean
    private CustomerRepository mockCustomerRepository;

    @BeforeEach
    void setUp() {
        Customer customer1 = Customer.builder().name("Jamal Robinson").address("Stockholm")
                .username("jamal").password(passwordEncoder.encode("1234")).id(1L).build();
        Customer customer2 = Customer.builder().name("David Beckham").username("david")
                .password(passwordEncoder.encode("1234")).address("Manchester").id(2L).build();
        Customer customer3 = Customer.builder().name("Koke").address("Madrid").username("koke")
                .password(passwordEncoder.encode("1234")).id(3L).build();

        Item item1 = Item.builder().id(1L).name("MacBook Pro").build();
        Item item2 = Item.builder().id(2L).name("iPad").build();
        Item item3 = Item.builder().id(3L).name("iPhone").build();

        BuyOrder buyOrder1 = BuyOrder.builder().id(1L).customer(customer1).items(List.of(item1, item2, item3)).build();
        BuyOrder buyOrder2 = BuyOrder.builder().id(2L).customer(customer2).items(List.of(item3)).build();
        BuyOrder buyOrder3 = BuyOrder.builder().id(3L).customer(customer3).items(List.of(item2, item3)).build();

        when(mockCustomerRepository.findCustomerByUsername("jamal")).thenReturn(customer1);
        when(mockRepository.findById(1L)).thenReturn(Optional.of(buyOrder1));
        when(mockRepository.findAll()).thenReturn(List.of(buyOrder1, buyOrder2, buyOrder3));
        when(mockRepository.findAllByCustomerId(2L)).thenReturn(List.of(buyOrder2));
    }

    private String token() throws Exception {
        return mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                            "username": "jamal",
                                            "password": "1234"
                                        }
                                        """
                        ))
                .andReturn().getResponse().getContentAsString();
    }


    @Test
    @DisplayName("Get all orders with valid token")
    void getOrdersTest() throws Exception {
        mockMvc.perform(get("/orders")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token())).andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                 [{"id":1,"customerResponse":{"id":1,"name":"Jamal Robinson",
                                 "address":"Stockholm","username":"jamal"},
                                 "items":[{"id":1,"name":"MacBook Pro"},
                                 {"id":2,"name":"iPad"},
                                 {"id":3,"name":"iPhone"}]},
                                 {"id":2,"customerResponse":{"id":2,"name":"David Beckham",
                                 "address":"Manchester","username":"david"},
                                 "items":[{"id":3,"name":"iPhone"}]},
                                 {"id":3,"customerResponse":{"id":3,"name":"Koke",
                                 "address":"Madrid","username":"koke"},
                                 "items":[{"id":2,"name":"iPad"},{"id":3,"name":"iPhone"}]}]
                                """
                ));

    }

    @Test
    @DisplayName("Get all orders for a specific customer")
    void getOrdersByCustomerIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/2")
                        .header("Authorization", "Bearer " + token())).andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                [{"id":2,"customerResponse":
                                {"id":2,"name":"David Beckham",
                                "address":"Manchester","username":"david"},
                                "items":[{"id":3,"name":"iPhone"}]}]
                                """
                ));
    }

}