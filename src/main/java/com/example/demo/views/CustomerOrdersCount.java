package com.example.demo.views;

public class CustomerOrdersCount {
    private Long orderCount;
    private String customerName;
    public CustomerOrdersCount(Long orderCount, String customerName){
        this.orderCount = orderCount;
        this.customerName = customerName;
    }

    public Long getOrderCount() {
        return orderCount;
    }

    public String getCustomerName() {
        return customerName;
    }
}
