package com.example.api.controller;

import com.example.api.dto.BuyOrderResponse;
import com.example.api.dto.CustomerItemResponseDTO;
import com.example.api.error.CustomizedNotFoundException;
import com.example.api.model.Item;
import com.example.api.service.ItemServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemServiceImpl itemServiceImpl;

    @Operation(summary = "All items",
            description = "View all the items",
            tags = {"Items", "Get"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = Item.class)),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            ),
                            description = "Successful response"
                    ),
                    @ApiResponse(responseCode = "403",
                            description = "Authentication Issue",
                            content = @Content)
            })
    @GetMapping
    public Iterable<Item> getAllItems() {
        return itemServiceImpl.getAllItems();
    }

    @Operation( summary = "Get specific item",
            description = "Get an item by a given name",
            tags = {"Items", "Get"},
            parameters = {@Parameter(name = "name", description = "The path variable.", example = "Macbook Pro")},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = Item.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            ),
                            description = "Successful response"
                    ),
                    @ApiResponse(responseCode = "404",
                            description = "Item not found",
                            content = @Content),
                    @ApiResponse(responseCode = "403",
                            description = "Authentication Issue",
                            content = @Content)
            },
            security = {@SecurityRequirement(name = "BearerJWT")})
    @GetMapping("/{name}")
    public Item getItemByName(@PathVariable String name)
            throws CustomizedNotFoundException {
        return itemServiceImpl.getItemByName(name);
    }

    @Operation( summary = "Add item",
            description = "Adding a new item",
            tags = {"Items", "Post"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody
                    (
                            description = "Here the customer need to add a new item",
                            content = @Content
                                    (
                                            schema = @Schema(implementation = Item.class),
                                            examples = {@ExampleObject(name = "Adding an item",value = """
                                                    {
                                                      "name": "string"
                                                    }
                                                    """)}
                                    )

                    ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = Item.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            ),
                            description = "Successful response"
                    ),
                    @ApiResponse(responseCode = "403",
                            description = "Authentication Issue",
                            content = @Content)
            },
            security = {@SecurityRequirement(name = "BearerJWT")})
    @PostMapping
    public String addNewItem(@RequestBody Item item) {
        return itemServiceImpl.addNewItem(item);
    }

    @Operation(summary = "Buy a new item",
            description = "Buying a new item",
            tags = {"Items", "Post"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody
            (
                    description = "Here the customer need enter their id and item id",
                    content = @Content
                            (
                                    schema = @Schema(implementation = CustomerItemResponseDTO.class)
                            )

            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = Item.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            ),
                            description = "Successful response"
                    ),
                    @ApiResponse(responseCode = "403",
                            description = "Authentication Issue",
                            content = @Content)
            },
            security = {@SecurityRequirement(name = "BearerJWT")}
    )
    @PostMapping("/buy")
    public BuyOrderResponse getOrdersByCustomerIdAndItemId(@RequestBody CustomerItemResponseDTO customerItemResponseDTO,
                                                           @AuthenticationPrincipal UserDetails user)
            throws CustomizedNotFoundException {
        return itemServiceImpl.getBuyOrderResponsesByCustomerIdAndItemId(customerItemResponseDTO, user);
    }


}
