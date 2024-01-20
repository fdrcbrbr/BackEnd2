package com.example.frontend.service;

import com.example.frontend.vo.Item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemService {

    List<Item> getAllProducts();
}
