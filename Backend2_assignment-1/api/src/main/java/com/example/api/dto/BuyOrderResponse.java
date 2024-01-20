package com.example.api.dto;

import com.example.api.model.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyOrderResponse {

    private Long id;
    private CustomerResponse customerResponse;
    private List<Item> items;

}
