package com.hasan.m.shehata.crudyrestaurants.services;

import com.hasan.m.shehata.crudyrestaurants.models.Customer;
import com.hasan.m.shehata.crudyrestaurants.models.Order;

import java.util.List;

public interface CustomerService {
    public List<Customer> findAll();
    public Customer findById(long id);
    public Customer findByName(String name);
    public List<Order> findCustomerOrders(Customer customer);
    public List<Customer> findByNameLike(String thename);
    public void Save(Customer customer);
    public void update(Customer customer, long id);
    public void delete(long id);
}
