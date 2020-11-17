package com.example.demo.repositories;

import com.example.demo.models.Customer;
import com.example.demo.views.CustomerOrderCounts;
import com.example.demo.views.CustomerOrdersCount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long>{

    @Query(value="SELECT * " +
                 "FROM customers " +
                 "where custname ILIKE :part"
            , nativeQuery=true)
    List<Customer> findCustomersByPartOfNameIgnoringCase(String part);

    List<Customer> findCustomerByCustnameContainingIgnoringCase(String part);
    // Get all customers with a field of orders count with native query
    @Query(value =  "SELECT c.custname AS customername, " +
                    "count(ordnum) AS ordercount " +
                    "FROM customers c LEFT JOIN orders o " +
                    "ON c.custcode = o.custcode " +
                    "GROUP BY c.custname " +
                    "ORDER BY ordercount DESC"
            ,nativeQuery = true)
    List<CustomerOrderCounts> getOrderCountedCustomersNatively();
    // Get all customers with a field of orders count with non-native query
    @Query(value = "SELECT " +
            "            new com.example.demo.views.CustomerOrdersCount(count(o.ordnum), c.custname) " +
            "FROM Customer c LEFT JOIN Order o " +
            "ON c.custcode = o.customer.custcode " +
            "GROUP BY c.custname "
                , nativeQuery = false)
    List<CustomerOrdersCount> getOrderCountedCustomersNonNatively();


}