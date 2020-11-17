package com.example.demo.services;

import com.example.demo.models.Order;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;


    @Override
    public Order getById(long id) throws EntityNotFoundException {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order " + id + " not found!"));
        return order;
    }

    @Override
    public List<Order> getOrdersWithAdvanceAmountGreaterThanZero() {
        return orderRepository.findByAdvanceamountGreaterThan(0.0);
    }
}
