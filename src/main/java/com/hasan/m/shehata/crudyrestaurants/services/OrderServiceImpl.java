package com.hasan.m.shehata.crudyrestaurants.services;

import com.hasan.m.shehata.crudyrestaurants.models.Customer;
import com.hasan.m.shehata.crudyrestaurants.models.Order;
import com.hasan.m.shehata.crudyrestaurants.models.Payment;
import com.hasan.m.shehata.crudyrestaurants.repositories.CustomerRepository;
import com.hasan.m.shehata.crudyrestaurants.repositories.OrderRepository;
import com.hasan.m.shehata.crudyrestaurants.repositories.PaymentRepository;
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
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Order findById(long id) throws EntityNotFoundException {
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order " + id + " not found!"));
    }

    @Override
    public List<Order> findOrdersOfAdvanceAmountGreaterThanZero() {
        List<Order> newOrders = new ArrayList<>();
        List<Order> orders = orderRepository.findByAdvanceamountGreaterThan(0.0d);
        if(orders!=null && orders.size()>0) {
            orders.iterator().forEachRemaining(newOrders::add);
        }
        return newOrders;
    }

    @Transactional
    @Override
    public Order Save(Order order)  {
        Order newOrder;

        if(order.getOrdnum()!=0){
            newOrder = orderRepository.findById(order.getOrdnum()).orElseThrow(()->new EntityNotFoundException("Order " + order.getOrdnum() + " not found!"));
        }
        else{
            newOrder = new Order();
        }

        if(order.getCustomer() != null) {
            Customer customer = customerRepository.findById(order.getCustomer().getCustcode()).orElseThrow(()->new EntityNotFoundException("Customer " + order.getCustomer().getCustcode() + " is not found!"));
            newOrder.setCustomer(customer);
        }
        else{
            throw new EntityNotFoundException("Customer property are not defined in JSON of order!");
        }

        newOrder.setAdvanceamount(order.getAdvanceamount());

        newOrder.setOrdamount(order.getOrdamount());

        newOrder.setOrderdescription(order.getOrderdescription());

        newOrder.getPayments().clear();
        if(order.getPayments() != null && order.getPayments().size() > 0){
            order.getPayments().iterator().forEachRemaining(payment -> {
                Payment existingPayment = paymentRepository.findById(payment.getPaymentid()).orElseThrow(()->new EntityNotFoundException("Payment " + payment.getPaymentid() + " not found!"));
                newOrder.getPayments().add(existingPayment);
            });
        }

        return orderRepository.save(newOrder);



    }

    @Transactional
    @Override
    public void update(Order order, long id) {

    }

    @Transactional
    @Override
    public void delete(long id) {
        orderRepository.deleteById(id);
    }
}
