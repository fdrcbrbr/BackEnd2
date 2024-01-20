package com.example.api.controller;

import com.example.api.model.Customer;
import com.example.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class SignUpController {

    private final UserService userService;

    @Operation(
            summary = "Sign up",
            description = "This is to Sign up a new user",
            tags = {"Sign up", "Post"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody
                    (description = "Here the customer need to write his/her username and password",
                            content = @Content(
                                    examples = {@ExampleObject(name = "Sign up", value = """
                                            {
                                                "name": "string",
                                                "address": "string",
                                                "username": "string",
                                                "password": "string"
                                            }
                                            """)})),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "A New user is created",
                            content = @Content(
                                    schema = @Schema(implementation = Customer.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
            })
    @PostMapping("/sign_up")
    public ResponseEntity<Customer> saveUser(@RequestBody Customer user) {
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/sign_up").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }
}
