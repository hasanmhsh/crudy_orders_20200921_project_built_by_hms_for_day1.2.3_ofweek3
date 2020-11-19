package com.example.demo.controllers;

import com.example.demo.models.Customer;
import com.example.demo.services.CustomerService;
import com.example.demo.views.CustomerOrderCounts;
import com.example.demo.views.CustomerOrdersCount;
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
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping(value = "/orders",
            produces = "application/json")
    public ResponseEntity<?> listAll()
    {
        List<Customer> customers = customerService.getAll();
        return new ResponseEntity<>(customers,
                HttpStatus.OK);
    }

    @GetMapping(value = "/customer/{id}",
            produces = "application/json")
    public ResponseEntity<?> getById(@PathVariable long id)
    {
        Customer customer = customerService.getById(id);
        return new ResponseEntity<>(customer,
                HttpStatus.OK);
    }

    @GetMapping(value = "/namelike/{part}",
            produces = "application/json")
    public ResponseEntity<?> getByNameLikeCustom(@PathVariable String part)
    {
        List<Customer> customers = customerService.getByPartOfNameCustomQuery(part);
        return new ResponseEntity<>(customers,
                HttpStatus.OK);
    }

    @GetMapping(value = "/namelike/{part}/jpa",
            produces = "application/json")
    public ResponseEntity<?> getByNameLikeJPA(@PathVariable String part)
    {
        List<Customer> customers = customerService.getByPartOfNameJPAQuery(part);
        return new ResponseEntity<>(customers,
                HttpStatus.OK);
    }

    @GetMapping(value = "/orders/count",
            produces = "application/json")
    public ResponseEntity<?> getOrdersCountWithNativeQuery()
    {
        List<CustomerOrderCounts> customers = customerService.getCustomerOrderCountsWithNativeQuery();
        return new ResponseEntity<>(customers,
                HttpStatus.OK);
    }

    @GetMapping(value = "/orders/countss",
            produces = "application/json")
    public ResponseEntity<?> getOrdersCountWithNonNativeQuery()
    {
        List<CustomerOrdersCount> customers = customerService.getCustomerOrderCountsWithNonNativeQuery();
        return new ResponseEntity<>(customers,
                HttpStatus.OK);
    }

    @PostMapping(value = "/customer", consumes = "application/json")
    public ResponseEntity<?> createNewCustomer(@RequestBody Customer customer){
        customer.setCustcode(0);
        final Customer c = customer;
        Object response = new Object(){
            public final String created = "successful";
            public final Customer customer = customerService.createNewIfIdIsZeroElseReplaceExisting(c);
        };
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(value = "/customer/{id}", consumes = "application/json")
    public ResponseEntity<?> replaceExistingCustomer(@PathVariable long id, @RequestBody Customer customer){
        customer.setCustcode(id);
        final Customer c = customerService.createNewIfIdIsZeroElseReplaceExisting(customer);
        Object response = new Object(){
            public final Boolean replaced = true;
            public final Customer customer = c;
        };
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(value = "/customer/{id}", consumes = "application/json")
    public ResponseEntity<?> updateExistingCustomer(@PathVariable long id, @RequestBody Customer customer){
        final Customer c = customerService.updateExisting(customer,id);
        Object response = new Object(){
            public final Boolean updated = true;
            public final Customer customer = c;
        };
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/customer/{id}", produces = "application/json")
    public ResponseEntity<?> deleteCustomer(@PathVariable long id){
        customerService.delete(id);
        return new ResponseEntity<>(
                new Object(){
                    public final String deleted = "successful";
                    public final Long deletedCustomer = id;
                    public final List<Customer> customers = customerService.getAll();
                }
                ,HttpStatus.OK
        );
    }
}
