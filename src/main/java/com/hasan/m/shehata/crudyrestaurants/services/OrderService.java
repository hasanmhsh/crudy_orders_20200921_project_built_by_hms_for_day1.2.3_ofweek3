package com.hasan.m.shehata.crudyrestaurants.services;

import com.hasan.m.shehata.crudyrestaurants.models.Customer;
import com.hasan.m.shehata.crudyrestaurants.models.Order;

import java.util.List;

public interface OrderService {
    public Order findById(long id);
    public Order findOrderByAdvanceAmount(double advanceAmount);
    public List<Order> findOrdersOfAdvanceAmountGreaterThanZero();
    public void Save(Order order);
    public void update(Order order, long id);
    public void delete(long id);
}
