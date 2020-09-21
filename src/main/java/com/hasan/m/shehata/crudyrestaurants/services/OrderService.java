package com.hasan.m.shehata.crudyrestaurants.services;

import com.hasan.m.shehata.crudyrestaurants.models.Order;

import java.util.List;

public interface OrderService {
    public Order findById(long id);
    public List<Order> findOrdersOfAdvanceAmountGreaterThanZero();
    public Order Save(Order order);
    public void update(Order order, long id);
    public void delete(long id);
}
