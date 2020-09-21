package com.hasan.m.shehata.crudyrestaurants.services;

import com.hasan.m.shehata.crudyrestaurants.models.Agent;
import com.hasan.m.shehata.crudyrestaurants.models.Customer;
import com.hasan.m.shehata.crudyrestaurants.models.Order;
import com.hasan.m.shehata.crudyrestaurants.models.Payment;
import com.hasan.m.shehata.crudyrestaurants.repositories.AgentRepository;
import com.hasan.m.shehata.crudyrestaurants.repositories.CustomerRepository;
import com.hasan.m.shehata.crudyrestaurants.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Transactional
@Service(value = "customerService")
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public List<Customer> findAll() {

        List<Customer> customers = new ArrayList<>();
        customerRepository.findAll().iterator().forEachRemaining(customers::add);
        return customers;
    }

    @Override
    public Customer findById(long id) throws EntityNotFoundException {
        return customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer " + String.valueOf(id) + " not found"));
    }

    @Override
    public Customer findByName(String name) throws EntityNotFoundException {
        Customer customer = customerRepository.findByCustname(name);
        if (customer == null)
            throw new EntityNotFoundException("Customer " + name + "not found!");
        else
            return customer;
    }

    @Override
    public List<Order> findCustomerOrders(long custcode) throws EntityNotFoundException {
        Customer customer = customerRepository.findById(custcode).orElseThrow(()->new EntityNotFoundException("Customer " + String.valueOf(custcode) + " not found"));
        List<Order> orders = new ArrayList<>();
        customer.getOrders().iterator().forEachRemaining(orders::add);
        return orders;
    }

    @Override
    public List<Customer> findByNameLike(String thename) {
        List<Customer> customers = new ArrayList<>();
        customerRepository.findByCustnameContainingIgnoringCase(thename).iterator().forEachRemaining(customers::add);
        return customers;
    }

    @Transactional
    @Override
    public Customer Save(Customer customer) throws EntityNotFoundException {
        Customer newCustomer;
        if(customer.getCustcode() != 0){
            newCustomer = customerRepository.findById(customer.getCustcode()).orElseThrow(()->new EntityExistsException("Customer " + customer.getCustcode() + " not found!"));
        }
        else{
            newCustomer = new Customer();
        }
        Agent agent = agentRepository.findById(customer.getAgent().getAgentcode()).orElseThrow(()->new EntityNotFoundException("Agent " + customer.getAgent().getAgentcode() + " not found!"));
        newCustomer.setAgent(agent);
        newCustomer.setCustcity(customer.getCustcity());
        newCustomer.setCustcountry(customer.getCustcountry());
        newCustomer.setCustname(customer.getCustname());
        newCustomer.setGrade(customer.getGrade());
        newCustomer.setOpeningamt(customer.getOpeningamt());
        newCustomer.setReceiveamt(customer.getReceiveamt());
        newCustomer.setOutstandingamt(customer.getOutstandingamt());
        newCustomer.setPaymentamt(customer.getPaymentamt());
        newCustomer.setPhone(customer.getPhone());
        newCustomer.setWorkingarea(customer.getWorkingarea());
        if(customer.getOrders()!=null && customer.getOrders().size()>0) {
            List<Order> orders = newCustomer.getOrders();
            orders.clear();
            customer.getOrders().iterator().forEachRemaining(order -> {
                Order newOrder = new Order(order);
                Set<Payment> orderPayments = order.getPayments();
                if(orderPayments!=null){
                    orderPayments.iterator().forEachRemaining(payment -> {
                        Payment newPayment = paymentRepository.findById(payment.getPaymentid()).orElseThrow(()->new EntityNotFoundException("Payment " + payment.getPaymentid() + " not found!"));
                        newOrder.addPayments(newPayment);
                    });
                }
                newOrder.setCustomer(newCustomer);
                orders.add(newOrder);

            });
        }

        return customerRepository.save(newCustomer);
    }

    @Transactional
    @Override
    public Customer update(Customer customer, long id) throws EntityNotFoundException {
        Customer existingCustomer = customerRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Customer " + id + " not found!"));
        if(customer.getAgent() != null) {
            Agent agent = agentRepository.findById(customer.getAgent().getAgentcode()).orElseThrow(()->new EntityNotFoundException("Agent " + customer.getAgent().getAgentcode() + " not found!"));
            existingCustomer.setAgent(agent);
        }
        if(customer.getCustcity() != null) {
            existingCustomer.setCustcity(customer.getCustcity());
        }
        if(customer.getCustcountry() != null) {
            existingCustomer.setCustcountry(customer.getCustcountry());
        }
        if(customer.getCustname() != null) {
            existingCustomer.setCustname(customer.getCustname());
        }
        if(customer.getGrade() != null) {
            existingCustomer.setGrade(customer.getGrade());
        }
        if(customer.getOpeningamt() != 0.0d) {
            existingCustomer.setOpeningamt(customer.getOpeningamt());
        }
        if(customer.getReceiveamt() != 0.0d) {
            existingCustomer.setReceiveamt(customer.getReceiveamt());
        }
        if(customer.getOutstandingamt() != 0.0d) {
            existingCustomer.setOutstandingamt(customer.getOutstandingamt());
        }
        if(customer.getPaymentamt() != 0.0d) {
            existingCustomer.setPaymentamt(customer.getPaymentamt());
        }
        if(customer.getPhone() != null) {
            existingCustomer.setPhone(customer.getPhone());
        }
        if(customer.getWorkingarea() != null) {
            existingCustomer.setWorkingarea(customer.getWorkingarea());
        }
        List<Order> orders = customer.getOrders();
        if(orders != null && orders.size()>0){
            List<Order> existingOrders = existingCustomer.getOrders();
            existingOrders.clear();
            orders.iterator().forEachRemaining(order -> {
                Order newOrder = new Order(order);
                Set<Payment> orderPayments = order.getPayments();
                if(orderPayments!=null){
                    orderPayments.iterator().forEachRemaining(payment -> {
                        Payment newPayment = paymentRepository.findById(payment.getPaymentid()).orElseThrow(()->new EntityNotFoundException("Payment " + payment.getPaymentid() + " not found!"));
                        newOrder.addPayments(newPayment);
                    });
                }
                newOrder.setCustomer(existingCustomer);
                existingOrders.add(newOrder);

            });
        }
        return customerRepository.save(existingCustomer);
    }


    @Transactional
    @Override
    public void delete(long id) {
        customerRepository.deleteById(id);
    }
}
