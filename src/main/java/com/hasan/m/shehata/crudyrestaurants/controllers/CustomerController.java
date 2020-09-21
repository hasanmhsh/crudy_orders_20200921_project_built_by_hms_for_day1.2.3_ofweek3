package com.hasan.m.shehata.crudyrestaurants.controllers;

import com.hasan.m.shehata.crudyrestaurants.models.Customer;
import com.hasan.m.shehata.crudyrestaurants.services.CustomerService;
import org.aspectj.weaver.patterns.HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

//    GET /customers/orders - Returns all customers with their orders
    @GetMapping(value = "/orders",produces = "application/json")
    public ResponseEntity<?> listAllCustomersWIthTheirOrders(){
        List<Customer> customers = customerService.findAll();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }


//    GET /customers/customer/{id} - Returns the customer and their orders with the given customer id
    @GetMapping(value = "/customer/{id}", produces = "application/json")
    public ResponseEntity<?> findCustomerById(@PathVariable long id){
        Customer customer = customerService.findById(id);
        return new ResponseEntity<>(customer,HttpStatus.OK);
    }


//    GET /customers/namelike/{likename} - Returns all customers and their orders with a customer name containing the given substring
    @GetMapping(value = "/namelike/{likename}", produces = "application/json")
    public ResponseEntity<?> findCustomersByPartOfName(@PathVariable String likename){
        List<Customer> customers = customerService.findByNameLike(likename);
        return new ResponseEntity<>(customers,HttpStatus.OK);
    }




//
//    GET /customers/orders/count - Using a custom query, return a list of all customers with the number of orders they have placed.
//



    ///


//    POST /customers/customer - Adds a new customer including any new orders
    @PostMapping(value = "/customer",consumes = "application/json",produces = "application/json")
    public ResponseEntity<?> createNewCustomer(@RequestBody Customer customer){
        Customer customer1 = customerService.Save(customer);
        return new ResponseEntity<>(customer1,HttpStatus.OK);
    }

//    PUT /customers/customer/{custcode} - completely replaces the customer record including associated orders with the provided data
    @PutMapping(value = "/customer/{id}",consumes = "application/json",produces = "application/json")
    public ResponseEntity<?> replaceExistingWithNewCustomer(@PathVariable long id,@RequestBody Customer customer){
        customer.setCustcode(id);
        Customer customer1 = customerService.Save(customer);
        return new ResponseEntity<>(customer1,HttpStatus.OK);
    }

//    PATCH /customers/customer/{custcode} - updates customers with the new data. Only the new data is to be sent from the frontend client.
    @PatchMapping(value = "/customer/{id}",consumes = "application/json",produces = "application/json")
    public ResponseEntity<?> updateExistingCustomer(@PathVariable long id,@RequestBody Customer customer){
        customer.setCustcode(id);
        Customer customer1 = customerService.update(customer,id);
        return new ResponseEntity<>(customer1,HttpStatus.OK);
    }

//    DELETE /customers/customer/{custcode} - Deletes the given customer including any associated orders
    @DeleteMapping(value = "/customer/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable long id){
        customerService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
