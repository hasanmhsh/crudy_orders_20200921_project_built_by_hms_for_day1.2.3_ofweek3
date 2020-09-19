package com.hasan.m.shehata.crudyrestaurants.repositories;
import com.hasan.m.shehata.crudyrestaurants.models.Agent;
import com.hasan.m.shehata.crudyrestaurants.models.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, Long> {
}
