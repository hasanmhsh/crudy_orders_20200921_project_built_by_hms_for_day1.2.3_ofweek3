package com.hasan.m.shehata.crudyrestaurants.repositories;
import com.hasan.m.shehata.crudyrestaurants.models.Agent;
import com.hasan.m.shehata.crudyrestaurants.models.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByAdvanceamountGreaterThan(double value);

}
