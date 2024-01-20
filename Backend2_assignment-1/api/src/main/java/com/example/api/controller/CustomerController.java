package com.example.api.controller;

import com.example.api.dto.AuthenticationRequest;
import com.example.api.dto.AuthenticationResponse;
import com.example.api.dto.CustomerResponse;
import com.example.api.error.CustomizedNotFoundException;
import com.example.api.model.Customer;
import com.example.api.service.CustomerServiceImpl;
import com.example.api.service.UserService;
import com.example.api.util.JWTUtility;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final JWTUtility jwtUtility;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final CustomerServiceImpl customerServiceImpl;

    @Operation(summary = "All Customers",
            description = "View all the customers",
            tags = {"Customer", "Get"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = CustomerResponse.class)),
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
    @GetMapping("/customers")
    public List<CustomerResponse> getAllCustomers() {
        return customerServiceImpl.getAllCustomersResponses();
    }

    @Operation(
            summary = "Get specific customer",
            description = "Get a customer by a given id",
            tags = {"Customer", "Get"},
            parameters = {@Parameter(name = "id", description = "The path variable.", example = "1")},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = CustomerResponse.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            ),
                            description = "Successful response"
                    ),
                    @ApiResponse(responseCode = "403",
                            description = "Authentication Issue",
                            content = @Content),
                    @ApiResponse(responseCode = "404",
                            description = "Customer not found",
                            content = @Content)
            },
            security = {@SecurityRequirement(name = "BearerJWT")}
    )
    @GetMapping("/customers/{id}")
    public CustomerResponse getCustomerResponseFromCustomerById(@PathVariable Long id)
            throws CustomizedNotFoundException {
        return customerServiceImpl.getCustomerResponseFromCustomerById(id);
    }

    @Operation(
            summary = "Add Customer",
            description = "Adding a new customer",
            tags =
                    {
                            "Customer",
                            "Post"
                    },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody
                    (
                            description = "Here the customer need to write his/her username and password",
                            content = @Content
                                    (
                                            schema = @Schema(implementation = Customer.class),
                                            examples = {@ExampleObject(name = "Adding a customer",value = """
                                                    {
                                                      "name": "string",
                                                      "address": "string",
                                                      "username": "string",
                                                      "password": "string"
                                                    }
                                                    """)}
                                    )

                    ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = Customer.class),
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
    @PostMapping("/customers")
    public String add(@RequestBody Customer c) {
        return customerServiceImpl.add(c);
    }

    @Operation(
            summary = "log in",
            description = "User to log in",
            tags = {
                    "Customer",
                    "Post"
            }
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User logged in",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = AuthenticationRequest.class)
                                    )
                            }
                    ),
                    @ApiResponse(responseCode = "404",
                            description = "Username not found",
                            content = @Content),
                    @ApiResponse(responseCode = "403",
                            description = "Authentication Issue",
                            content = @Content)
            }
    )
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest
    )
            throws Exception {

        customerServiceImpl.authenticatingRequest(authenticationRequest);

        final UserDetails userDetails
                = userService.loadUserByUsername(authenticationRequest.getUsername());

        final String token =
                jwtUtility.generateToken(userDetails);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse(token);
        log.info("Token is: {}", authenticationResponse);
        return ResponseEntity.ok(authenticationResponse.getJwtToken());
    }

}
