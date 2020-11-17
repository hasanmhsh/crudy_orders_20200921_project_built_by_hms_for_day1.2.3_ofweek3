package com.example.demo.services;

import com.example.demo.models.Customer;
import com.example.demo.views.CustomerOrderCounts;
import com.example.demo.views.CustomerOrdersCount;

import java.util.List;

public interface CustomerService {
    List<Customer> getAll();
    Customer getById(long id);
    List<Customer> getByPartOfNameCustomQuery(String part);
    List<Customer> getByPartOfNameJPAQuery(String part);
    List<CustomerOrderCounts> getCustomerOrderCountsWithNativeQuery();
    List<CustomerOrdersCount> getCustomerOrderCountsWithNonNativeQuery();
    Customer createNewIfIdIsZeroElseReplaceExisting(Customer customer);
    Customer updateExisting(Customer customer, long id);
    void delete(long id);
}
