package com.example.api.dto;

import com.example.api.model.BuyOrder;
import com.example.api.model.Customer;
import com.example.api.model.Item;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

    public static CustomerResponse customerToCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId()).name(customer.getName())
                .username(customer.getUsername()).address(customer.getAddress()).build();
    }

    public static List<CustomerResponse> customersToCustomerResponses(List<Customer> customers) {
        List<CustomerResponse> customerResponse = new ArrayList<>();
        for (Customer customer: customers) {
            customerResponse.add(customerToCustomerResponse(customer));
        }
        return customerResponse;
    }

    public static BuyOrderResponse buyOrderToBuyOrderResponse(BuyOrder buyOrder){
        Customer customer = buyOrder.getCustomer();
        CustomerResponse customerResponse = customerToCustomerResponse(customer);
        List<Item> items = buyOrder.getItems();
        return BuyOrderResponse.builder().id(buyOrder.getId())
                .customerResponse(customerResponse).items(items).build();
    }

    public static List<BuyOrderResponse> buyOrderToBuyOrderResponses(List<BuyOrder> buyOrders){
        List<BuyOrderResponse> buyOrderResponse = new ArrayList<>();
        for(BuyOrder buyOrder : buyOrders){
            buyOrderResponse.add(buyOrderToBuyOrderResponse(buyOrder));
        }
        return buyOrderResponse;
    }


}
