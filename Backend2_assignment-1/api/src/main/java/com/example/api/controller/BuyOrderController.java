package com.example.api.controller;

import com.example.api.dto.BuyOrderResponse;
import com.example.api.error.CustomizedNotFoundException;
import com.example.api.service.BuyOrderServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class BuyOrderController {
    private final BuyOrderServiceImpl buyOrderServiceImpl;

    @Operation(summary = "All orders",
            tags = {"Buy Order", "Get"},
            description = "View all the orders",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = BuyOrderResponse.class)),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            ),
                            description = "Successful response"
                    ),
                    @ApiResponse(responseCode = "403",
                            description = "Authentication Issue",
                            content = @Content)
            },
            security = {@SecurityRequirement(name = "BearerJWT")})
    @GetMapping
    public List <BuyOrderResponse> getOrders(){
        return buyOrderServiceImpl.getAllOrders();
    }

    @Operation(summary = "Get specific order",
            description = "Get all orders by a given customerId",
            tags = {"Buy Order", "Get"},
            parameters = {@Parameter(name = "customerId", description = "The path variable.", example = "1")},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = BuyOrderResponse.class)),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            ),
                            description = "Successful response"
                    ),
                    @ApiResponse(responseCode = "404",
                            description = "Order not found",
                            content = @Content),
                    @ApiResponse(responseCode = "403",
                            description = "Authentication Issue",
                            content = @Content)
            },
            security = {@SecurityRequirement(name = "BearerJWT")})
    @GetMapping("/{customerId}")
    public List<BuyOrderResponse> getOrdersByCustomerId (@PathVariable Long customerId)
            throws CustomizedNotFoundException {
        return buyOrderServiceImpl.getOrderResponsesByCustomerId(customerId);
    }

}
