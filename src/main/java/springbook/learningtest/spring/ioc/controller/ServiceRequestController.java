package springbook.learningtest.spring.ioc.controller;

import org.springframework.mock.web.MockHttpServletRequest;
import springbook.learningtest.spring.ioc.bean.Customer;
import springbook.learningtest.spring.ioc.dao.CustomerDao;
import springbook.learningtest.spring.ioc.dao.ServiceRequestDao;
import springbook.learningtest.spring.ioc.service.EmailService;
import springbook.learningtest.spring.ioc.dto.ServiceRequest;

public class ServiceRequestController {

    CustomerDao customerDao;
    ServiceRequestDao serviceRequestDao;
    EmailService emailService;

    public void serviceRequestFormSubmit(MockHttpServletRequest request){
        ServiceRequest serviceRequest = new ServiceRequest();
        serviceRequest.setCustomerNo(request.getParameter("custNo"));;
        this.addNewServiceRequest(serviceRequest);
    }

    public void addNewServiceRequest(ServiceRequest serviceRequest){
        Customer customer
                = this.customerDao.findCustomerByNo(serviceRequest.getCustomerNo());
        this.serviceRequestDao.add(serviceRequest, customer);
        this.emailService.sendEmail(customer.getEmail, "A/S 접수가 정상적으로 처리되었습니다.");
    }

}
