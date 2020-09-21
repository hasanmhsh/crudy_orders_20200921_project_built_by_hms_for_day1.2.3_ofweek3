package com.hasan.m.shehata.crudyrestaurants.controllers;
import com.hasan.m.shehata.crudyrestaurants.models.Customer;
import com.hasan.m.shehata.crudyrestaurants.models.Order;
import com.hasan.m.shehata.crudyrestaurants.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    //    GET /orders/order/{id} - Returns the order and its customer with the given order number
    @GetMapping(value = "/order/{id}", produces = "application/json")
    public ResponseEntity<?> findOrderById(@PathVariable long id){
        Order order = orderService.findById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    //            Stretch Goal
    //
    //    GET /orders/advanceamount - returns all orders with their customers that have an advanceamount greater than 0.
    @GetMapping(value = "/order/advanceamount", produces = "application/json")
    public ResponseEntity<?> findOrderByAdvanceAmountGreaterThanZero(){
        List<Order> orders = orderService.findOrdersOfAdvanceAmountGreaterThanZero();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }




    //




    //    POST /orders/order - adds a new order to an existing customer
    @PostMapping(value = "/order",consumes = "application/json",produces = "application/json")
    public ResponseEntity<?> createNewOrder(@RequestBody Order order){
        Order order1 = orderService.Save(order);
        return new ResponseEntity<>(order1,HttpStatus.OK);
    }

    //    PUT /orders/order/{ordernum} - completely replaces the given order record
    @PutMapping(value = "/order/{id}",consumes = "application/json",produces = "application/json")
    public ResponseEntity<?> replaceExistingWithNewOrder(@PathVariable long id,@RequestBody Order order){
        order.setOrdnum(id);
        Order order1 = orderService.Save(order);
        return new ResponseEntity<>(order1,HttpStatus.OK);
    }

    //    DELETE /orders/order/{ordername} - deletes the given order
    @DeleteMapping(value = "/order/{id}")
    public ResponseEntity<?> deleteorder(@PathVariable long id){
        orderService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
