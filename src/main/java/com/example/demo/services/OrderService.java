package com.example.demo.services;

import com.example.demo.models.Order;

import java.util.List;

public interface OrderService {
    public List<Order> getAll();
    Order getById(long id);
    List<Order> getOrdersWithAdvanceAmountGreaterThanZero();

    Order createNewIfIdIsZeroElseReplaceExisting(Order order);
    Order updateExisting(Order order, long id);
    void delete(long id);
}
