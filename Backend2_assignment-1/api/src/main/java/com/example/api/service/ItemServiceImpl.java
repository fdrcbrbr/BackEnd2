package com.example.api.service;

import com.example.api.dto.*;
import com.example.api.error.CustomizedNotFoundException;
import com.example.api.model.BuyOrder;
import com.example.api.model.Customer;
import com.example.api.model.Item;
import com.example.api.rabbitmq.RabbitMQConfig;
import com.example.api.rabbitmq.RabbitMQMessageProducer;
import com.example.api.repository.BuyOrderRepository;
import com.example.api.repository.CustomerRepository;
import com.example.api.repository.ItemRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record ItemServiceImpl(ItemRepository itemRepository,
                              CustomerRepository customerRepository,
                              RabbitMQMessageProducer rabbitMQMessageProducer,
                              BuyOrderRepository buyOrderRepository)
        implements ItemService{

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item getItemByName(String name)
            throws CustomizedNotFoundException {
        Item itemByName = itemRepository.findItemByName(name);
        if (itemByName == null) {
            throw new CustomizedNotFoundException(
                    "The item: " + name + " could not be found");
        }
        return itemByName;
    }

    @Override
    public String addNewItem(Item item) {
        itemRepository.save(item);
        return "Saved";
    }

    @Override
    public BuyOrder getOrdersByCustomerIdAndItemId(CustomerItemResponseDTO customerItemResponseDTO,
                                                   UserDetails user)
            throws CustomizedNotFoundException {
        Customer customer = getCustomer(customerItemResponseDTO);
        if (!customer.getUsername().equals(user.getUsername())) {
            throw new CustomizedNotFoundException("NOT_AUTHORIZED");
        }
        Item item = getItem(customerItemResponseDTO);
        BuyOrder buyOrder = BuyOrder.builder().customer(customer).items(List.of(item)).build();

        ReceiptRequest receiptRequest = getReceiptRequest(customer, item);
        publishingReceiptRequest(receiptRequest);
        buyOrderRepository.save(buyOrder);
        return buyOrder;
    }

    @Override
    public BuyOrderResponse getBuyOrderResponsesByCustomerIdAndItemId(CustomerItemResponseDTO customerItemResponseDTO,
                                                   UserDetails user)
            throws CustomizedNotFoundException {
        return Mapper.buyOrderToBuyOrderResponse(getOrdersByCustomerIdAndItemId(customerItemResponseDTO, user));
    }

    private Customer getCustomer(CustomerItemResponseDTO customerItemResponseDTO)
            throws CustomizedNotFoundException {
        return customerRepository.findById(customerItemResponseDTO.getCustomerId()).orElseThrow(() ->
                new CustomizedNotFoundException(
                        "Customer with id: " + customerItemResponseDTO.getCustomerId() + " could not be found"));
    }

    private Item getItem(CustomerItemResponseDTO customerItemResponseDTO)
            throws CustomizedNotFoundException {
        return itemRepository.findById(customerItemResponseDTO.getItemId()).orElseThrow(() ->
                new CustomizedNotFoundException(
                        "Item with id: " + customerItemResponseDTO.getItemId() + " could not be found"));
    }

    private void publishingReceiptRequest(ReceiptRequest receiptRequest) {
        rabbitMQMessageProducer.publish(
                receiptRequest,
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY
        );
    }

    private ReceiptRequest getReceiptRequest(Customer customer, Item item) {
        return new ReceiptRequest(
                customer.getId(),
                customer.getUsername(),
                String.format("Hi %s, welcome to our web-shop and thanks for buying from us...", customer.getName()),
                item.getId(),
                item.getName()
        );
    }
}
