package com.example.api.service;

import com.example.api.dto.BuyOrderResponse;
import com.example.api.dto.CustomerItemResponseDTO;
import com.example.api.error.CustomizedNotFoundException;
import com.example.api.model.BuyOrder;
import com.example.api.model.Item;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface ItemService {

    List<Item> getAllItems();
    Item getItemByName(String name) throws CustomizedNotFoundException;
    String addNewItem(Item item);
    BuyOrder getOrdersByCustomerIdAndItemId(CustomerItemResponseDTO customerItemResponseDTO,
                                            UserDetails user)
            throws CustomizedNotFoundException;
    BuyOrderResponse getBuyOrderResponsesByCustomerIdAndItemId(CustomerItemResponseDTO customerItemResponseDTO,
                                                               UserDetails user)
            throws CustomizedNotFoundException;
}
