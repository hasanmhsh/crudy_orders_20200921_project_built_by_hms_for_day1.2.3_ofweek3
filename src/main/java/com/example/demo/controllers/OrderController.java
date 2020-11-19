package com.example.demo.controllers;

import com.example.demo.models.Customer;
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

    @PostMapping(value = "/order", consumes = "application/json")
    public ResponseEntity<?> createNewOrder(@RequestBody Order order){
        order.setOrdnum(0);
        final Order o = order;
        Object response = new Object(){
            public final String created = "successful";
            public final Order order = orderService.createNewIfIdIsZeroElseReplaceExisting(o);
        };
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(value = "/order/{id}", consumes = "application/json")
    public ResponseEntity<?> replaceExistingOrder(@PathVariable long id, @RequestBody Order order){
        order.setOrdnum(id);
        final Order o = orderService.createNewIfIdIsZeroElseReplaceExisting(order);
        Object response = new Object(){
            public final Boolean replaced = true;
            public final Order order = o;
        };
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/order/{id}", produces = "application/json")
    public ResponseEntity<?> deleteOrder(@PathVariable long id){
        orderService.delete(id);
        return new ResponseEntity<>(
                new Object(){
                    public final String deleted = "successful";
                    public final Long deletedOrder = id;
                    public final List<Order> orders = orderService.getAll();
                }
                ,HttpStatus.OK
        );
    }
}
