package com.example.demo.services;

import com.example.demo.models.Agent;
import com.example.demo.models.Customer;
import com.example.demo.models.Order;
import com.example.demo.models.Payment;
import com.example.demo.repositories.AgentRepository;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.PaymentRepository;
import com.example.demo.views.CustomerOrderCounts;
import com.example.demo.views.CustomerOrdersCount;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Transactional
@Service(value = "customerService")
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;


    @Override
    public List<Customer> getAll() {
        List<Customer> customers = new ArrayList<>();
        customerRepository.findAll()
                .iterator()
                .forEachRemaining(customers::add);
        return customers;
    }

    @Override
    public Customer getById(long id) throws EntityNotFoundException {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " not found!"));
    }

    @Override
    public List<Customer> getByPartOfNameCustomQuery(String part) {
        List<Customer> customers = customerRepository.findCustomersByPartOfNameIgnoringCase("%" + part + "%");
        return customers;
    }

    @Override
    public List<Customer> getByPartOfNameJPAQuery(String part) {
        List<Customer> customers = customerRepository.findCustomerByCustnameContainingIgnoringCase(part);
        return customers;
    }

    @Override
    public List<CustomerOrderCounts> getCustomerOrderCountsWithNativeQuery() {
        List<CustomerOrderCounts> list = customerRepository.getOrderCountedCustomersNatively();
        return list;
    }

    @Override
    public List<CustomerOrdersCount> getCustomerOrderCountsWithNonNativeQuery() {
        List<CustomerOrdersCount> list = customerRepository.getOrderCountedCustomersNonNatively();
        return list;
    }

    @Transactional
    @Override
    public Customer createNewIfIdIsZeroElseReplaceExisting(Customer customer) throws EntityNotFoundException, InvalidPropertyException {
        Customer newCustomer = null;
        Agent agent = null;
        if (customer.getAgent() != null && customer.getAgent()
                .getAgentcode() != 0) {
            agent = agentRepository.findById(customer.getAgent()
                    .getAgentcode())
                    .orElseThrow(() -> new EntityNotFoundException("Agent " + customer.getAgent()
                            .getAgentcode() + " not found!"));
        } else {
            throw new InvalidPropertyException(Customer.class, "Agent property is invalid , please include agent with agent code within customer json request!", "Example agent: {\"agentcode\": 4");
        }
        if (customer.getCustcode() == 0) {
            // Create new customer
            newCustomer = new Customer();
        } else {
            // Replace existing customer
            newCustomer = customerRepository.findById(customer.getCustcode())
                    .orElseThrow(() -> new EntityNotFoundException("Customer " + customer.getCustcode() + " not found!"));
        }
        newCustomer.setAgent(agent);
        newCustomer.setCustcity(customer.getCustcity());
        newCustomer.setCustcountry(customer.getCustcountry());
        newCustomer.setCustname(customer.getCustname());
        newCustomer.setGrade(customer.getGrade());
        newCustomer.setOpeningamt(customer.getOpeningamt());
        newCustomer.setOutstandingamt(customer.getOutstandingamt());
        newCustomer.setPaymentamt(customer.getPaymentamt());
        newCustomer.setReceiveamt(customer.getReceiveamt());
        newCustomer.setPhone(customer.getPhone());
        newCustomer.setWorkingarea(customer.getWorkingarea());

        newCustomer.getOrders()
                .clear();
        List<Order> newOrders = customer.getOrders();
        if (newOrders != null && newOrders.size() > 0) {
            for (Order order : newOrders) {
                Order newOrder = new Order();
                newOrder.setAdvanceamount(order.getAdvanceamount());
                newOrder.setCustomer(newCustomer);
                newOrder.setOrdamount(order.getOrdamount());
                newOrder.setOrderdescription(order.getOrderdescription());
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
                newCustomer.getOrders()
                        .add(newOrder);
            }
        }
        return customerRepository.save(newCustomer);
    }

    @Transactional
    @Override
    public Customer updateExisting(Customer customer, long id) throws EntityNotFoundException {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " not found!"));
        if (customer.getAgent() != null) {
            Agent agent = agentRepository.findById(customer.getAgent()
                    .getAgentcode())
                    .orElseThrow(() -> new EntityNotFoundException("Agent " + customer.getAgent()
                            .getAgentcode() + " not found!"));
            existingCustomer.setAgent(agent);
        }

        if (customer.getCustcity() != null)
            existingCustomer.setCustcity(customer.getCustcity());
        if (customer.getCustcountry() != null)
            existingCustomer.setCustcountry(customer.getCustcountry());
        if (customer.getCustname() != null)
            existingCustomer.setCustname(customer.getCustname());
        if (customer.getGrade() != null)
            existingCustomer.setGrade(customer.getGrade());
        if (customer.getOpeningamt() != 0)
            existingCustomer.setOpeningamt(customer.getOpeningamt());
        if (customer.getOutstandingamt() != 0)
            existingCustomer.setOutstandingamt(customer.getOutstandingamt());
        if (customer.getPaymentamt() != 0)
            existingCustomer.setPaymentamt(customer.getPaymentamt());
        if (customer.getReceiveamt() != 0)
            existingCustomer.setReceiveamt(customer.getReceiveamt());
        if (customer.getPhone() != null)
            existingCustomer.setPhone(customer.getPhone());
        if (customer.getWorkingarea() != null)
            existingCustomer.setWorkingarea(customer.getWorkingarea());

        if (customer.getOrders() != null && customer.getOrders()
                .size() > 0) {
            existingCustomer.getOrders()
                    .clear();
            List<Order> newOrders = customer.getOrders();
            for (Order order : newOrders) {
                Order newOrder = new Order();
                newOrder.setAdvanceamount(order.getAdvanceamount());
                newOrder.setCustomer(existingCustomer);
                newOrder.setOrdamount(order.getOrdamount());
                newOrder.setOrderdescription(order.getOrderdescription());
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
                existingCustomer.getOrders()
                        .add(newOrder);
            }
        }
        return customerRepository.save(existingCustomer);
    }

    @Transactional
    @Override
    public void delete(long id) throws EntityNotFoundException {
        customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " not found!"));
        customerRepository.deleteById(id);
    }
}
