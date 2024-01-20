//package com.example.api.bootstrap;
//
//import com.example.api.model.BuyOrder;
//import com.example.api.model.Customer;
//import com.example.api.model.Item;
//import com.example.api.repository.BuyOrderRepository;
//import com.example.api.service.UserService;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//
//@Configuration
//public class DataInitializer {
//
//    @Bean
//    public CommandLineRunner creatingData(BuyOrderRepository buyOrderRepository, UserService userService) {
//        return (args) -> {
//            Customer customer1 = Customer.builder()
//                    .name("Lennart Skoglund")
//                    .address("Stockholm")
//                    .username("lennart")
//                    .password("1234").id(1L).build();
//            Customer customer2 = Customer.builder()
//                    .name("David Beckham")
//                    .address("Manchester")
//                    .username("david")
//                    .password("1234").id(2L).build();
//            Customer customer3 = Customer.builder()
//                    .name("Jorge Resurrección Merodio")
//                    .address("Madrid")
//                    .username("koke")
//                    .password("1234").id(3L).build();
//
//            userService.saveUser(customer1);
//            userService.saveUser(customer2);
//            userService.saveUser(customer3);
//
//            Item item1 = Item.builder().id(1L).name("MacBook Pro").build();
//            Item item2 = Item.builder().id(2L).name("iPad").build();
//            Item item3 = Item.builder().id(3L).name("iPhone").build();
//
//            BuyOrder buyOrder1 = BuyOrder.builder().id(1L).customer(customer1).items(List.of(item1, item2, item3)).build();
//            BuyOrder buyOrder2 = BuyOrder.builder().id(2L).customer(customer2).items(List.of(item3)).build();
//            BuyOrder buyOrder3 = BuyOrder.builder().id(3L).customer(customer3).items(List.of(item2, item3)).build();
//
//            buyOrderRepository.save(buyOrder1);
//            buyOrderRepository.save(buyOrder2);
//            buyOrderRepository.save(buyOrder3);
//        };
//    }
//
//}
//
