package com.example.api.service;

import com.example.api.dto.BuyOrderResponse;
import com.example.api.dto.Mapper;
import com.example.api.error.CustomizedNotFoundException;
import com.example.api.model.BuyOrder;
import com.example.api.repository.BuyOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record BuyOrderServiceImpl(BuyOrderRepository buyOrderRepository)
        implements BuyOrderService {

    @Override
    public List<BuyOrder> getOrders() {
        return buyOrderRepository.findAll();
    }

    @Override
    public List<BuyOrderResponse> getAllOrders() {
        return Mapper.buyOrderToBuyOrderResponses(getOrders());
    }

    @Override
    public List<BuyOrder> getOrdersByCustomerId(Long customerId)
            throws CustomizedNotFoundException {
        List<BuyOrder> allByCustomerId = buyOrderRepository.findAllByCustomerId(customerId);
        if (allByCustomerId.isEmpty()) {
            throw new CustomizedNotFoundException(
                    "There are no orders available for customer with customer id: " + customerId
            );
        }
        return allByCustomerId;
    }

    @Override
    public List<BuyOrderResponse> getOrderResponsesByCustomerId(Long customerId)
            throws CustomizedNotFoundException {
        return Mapper.buyOrderToBuyOrderResponses(getOrdersByCustomerId(customerId));
    }
}
