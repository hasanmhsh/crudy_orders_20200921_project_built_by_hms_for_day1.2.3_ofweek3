package com.example.demo.repositories;

import com.example.demo.models.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long>{
    List<Order> findByAdvanceamountGreaterThan(double threshold);
}