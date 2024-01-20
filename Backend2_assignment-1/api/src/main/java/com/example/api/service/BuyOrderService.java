package com.example.api.service;

import com.example.api.dto.BuyOrderResponse;
import com.example.api.error.CustomizedNotFoundException;
import com.example.api.model.BuyOrder;

import java.util.List;

public interface BuyOrderService {

    List<BuyOrder> getOrders();
    List<BuyOrderResponse> getAllOrders();
    List<BuyOrder> getOrdersByCustomerId(Long customerId) throws CustomizedNotFoundException;
    List<BuyOrderResponse> getOrderResponsesByCustomerId(Long customerId) throws CustomizedNotFoundException;

}
