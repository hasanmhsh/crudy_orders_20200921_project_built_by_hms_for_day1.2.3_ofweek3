package com.example.demo.controllers;

import com.example.demo.models.Order;
import com.example.demo.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/order/{id}", produces = "application/json")
    private ResponseEntity<?> getOrderById(@PathVariable long id){
        Order order = orderService.getById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping(value = "/advanceamount", produces = "application/json")
    private ResponseEntity<?> getOrdersWhereAdvanceamountGreaterThanZero(){
        List<Order> orders = orderService.getOrdersWithAdvanceAmountGreaterThanZero();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
