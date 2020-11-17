package com.example.demo.services;

import com.example.demo.models.Agent;
import com.example.demo.models.Order;

import java.util.List;

public interface OrderService {
//    public List<Order> getAll();
    Order getById(long id);
    List<Order> getOrdersWithAdvanceAmountGreaterThanZero();
}
