package com.example.demo.services;

import com.example.demo.models.Agent;
import com.example.demo.models.Customer;
import com.example.demo.models.Order;
import com.example.demo.models.Payment;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.PaymentRepository;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        orderRepository.findAll()
                .iterator()
                .forEachRemaining(orders::add);
        return orders;
    }

    @Override
    public Order getById(long id) throws EntityNotFoundException {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order " + id + " not found!"));
        return order;
    }

    @Override
    public List<Order> getOrdersWithAdvanceAmountGreaterThanZero() {
        return orderRepository.findByAdvanceamountGreaterThan(0.0);
    }

    @Transactional
    @Override
    public Order createNewIfIdIsZeroElseReplaceExisting(Order order) throws EntityNotFoundException, InvalidPropertyException {
        Order newOrder = null;
        Customer cust = null;
        if (order.getCustomer() != null && order.getCustomer()
                .getCustcode() != 0) {
            cust = customerRepository.findById(order.getCustomer()
                    .getCustcode())
                    .orElseThrow(() -> new EntityNotFoundException("Customer " + order.getCustomer()
                            .getCustcode() + " not found!"));
        } else {
            throw new InvalidPropertyException(Order.class, "customer property is invalid , please include customer with customer code within order json request!", "Example customer: {\"custcode\": 4");
        }

        if (order.getOrdnum() == 0) {
            // Create new order
            newOrder = new Order();
        } else {
            // Replace existing order
            newOrder = orderRepository.findById(order.getOrdnum())
                    .orElseThrow(() -> new EntityNotFoundException("Order " + order.getOrdnum() + " not found!"));
        }
        newOrder.setCustomer(cust);
        newOrder.setOrderdescription(order.getOrderdescription());
        newOrder.setOrdamount(order.getOrdamount());
        newOrder.setAdvanceamount(order.getAdvanceamount());

        newOrder.getPayments()
                .clear();
        Set<Payment> newOrderPayments = order.getPayments();
        if (newOrderPayments != null && newOrderPayments.size() > 0) {
            for (Payment payment : newOrderPayments) {
                Payment existingPayment = paymentRepository.findById(payment.getPaymentid())
                        .orElseThrow(() -> new EntityNotFoundException("Payment " + payment.getPaymentid() + " not found!"));
                newOrder.addPayments(existingPayment);
            }
        }

        return orderRepository.save(newOrder);
    }

    @Transactional
    @Override
    public Order updateExisting(Order order, long id) throws EntityNotFoundException {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order " + id + " not found!"));
        if (order.getCustomer() != null && order.getCustomer()
                .getCustcode() != 0) {
            Customer cust = customerRepository.findById(order.getCustomer()
                    .getCustcode())
                    .orElseThrow(() -> new EntityNotFoundException("Customer " + order.getCustomer()
                            .getCustcode() + " not found!"));
            existingOrder.setCustomer(cust);
        }

        if (order.getOrderdescription() != null)
            existingOrder.setOrderdescription(order.getOrderdescription());
        if (order.getOrdamount() != 0)
            existingOrder.setOrdamount(order.getOrdamount());
        if (order.getAdvanceamount() != 0)
            existingOrder.setAdvanceamount(order.getAdvanceamount());

        if (order.getPayments() != null && order.getPayments()
                .size() > 0) {
            existingOrder.getPayments()
                    .clear();
            Set<Payment> newOrderPayments = order.getPayments();
            for (Payment payment : newOrderPayments) {
                Payment existingPayment = paymentRepository.findById(payment.getPaymentid())
                        .orElseThrow(() -> new EntityNotFoundException("Payment " + payment.getPaymentid() + " not found!"));
                existingOrder.addPayments(existingPayment);
            }
        }

        return orderRepository.save(existingOrder);
    }

    @Transactional
    @Override
    public void delete(long id) throws EntityNotFoundException {
        orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order " + id + " not found!"));
        orderRepository.deleteById(id);
    }
}
