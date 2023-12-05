package springbook.learningtest.spring.ioc.dao;

import springbook.learningtest.spring.ioc.bean.Customer;

public class CustomerDao {
    public Customer findCustomerByNo(String customerNo) {
        return new Customer();
    }

    public Customer getCustomer(int customerId) {
        return new Customer();
    }
}
