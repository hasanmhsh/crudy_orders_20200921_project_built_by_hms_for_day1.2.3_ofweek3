package com.hasan.m.shehata.crudyrestaurants.repositories;
import com.hasan.m.shehata.crudyrestaurants.models.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
