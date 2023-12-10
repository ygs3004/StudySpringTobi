package springbook.learningtest.spring.ioc.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import springbook.learningtest.spring.ioc.bean.Customer;
import springbook.learningtest.spring.ioc.dao.CustomerDao;
import springbook.learningtest.spring.ioc.service.EmailService;

@Component
@Scope("prototype")
public class ServiceRequest {

    Customer customer;
    String productNo;
    String description;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    EmailService emailService;

    public void setCustomerByCustomerId(int customerId){
        this.customer = this.customerDao.getCustomer(customerId);
    }

    public void setCustomerByCustomerNo(String customerNo){
        this.customer = customerDao.findCustomerByNo(customerNo);
    }

    public void notifyServiceRequestRegistration(){
        if(this.customer.serviceNotificationMethod == NotificationMethod.EMAIL){
            this.emailService.sendEmail(customer.getEmail(), "A/S 접수가 정상적으로 처리되었습니다.");
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
