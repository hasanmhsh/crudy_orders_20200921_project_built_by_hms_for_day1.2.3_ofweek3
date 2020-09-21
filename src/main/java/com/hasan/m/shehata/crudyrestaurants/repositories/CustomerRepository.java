package com.hasan.m.shehata.crudyrestaurants.repositories;
import com.hasan.m.shehata.crudyrestaurants.models.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    public List<Customer> findByCustnameContainingIgnoringCase(String part);

    public Customer findByCustname(String name);
}
