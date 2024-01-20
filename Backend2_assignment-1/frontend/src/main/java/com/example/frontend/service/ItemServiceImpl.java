package com.example.frontend.service;

import com.example.frontend.vo.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService{

    private final RestTemplate restTemplate;

    @Value("${template.data-binder.base-url}")
    private String apiBaseUrl;

    public ItemServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Item> getAllProducts() {
        ResponseEntity<Item[]> responseEntity =
                restTemplate.getForEntity(apiBaseUrl + "/items", Item[].class);
        Item[] ItemArray = responseEntity.getBody();
        assert ItemArray != null;
        return Arrays.stream(ItemArray)
                .collect(Collectors.toList());
    }
}
