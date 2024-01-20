package com.example.api.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
class JwtFilterTest {

    @Autowired
    JwtFilter jwtFilter;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;

    @BeforeEach
    void setUp() {
        // Given a request, response and filter chain
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        chain = mock(FilterChain.class);
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Hello");
    }

    @Test
    @DisplayName("Filter test")
    void doFilterInternal() throws ServletException, IOException {
        // When the filter processes the request
        jwtFilter.doFilterInternal(request, response, chain);

        // It should call the next filter in the chain
        verify(chain).doFilter(any(), same(response));
    }
}