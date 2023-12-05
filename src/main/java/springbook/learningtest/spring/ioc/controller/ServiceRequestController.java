package springbook.learningtest.spring.ioc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import springbook.learningtest.spring.ioc.dao.CustomerDao;
import springbook.learningtest.spring.ioc.dao.ServiceRequestDao;
import springbook.learningtest.spring.ioc.service.EmailService;
import springbook.learningtest.spring.ioc.dto.ServiceRequest;

public class ServiceRequestController {

    CustomerDao customerDao;
    ServiceRequestDao serviceRequestDao;
    // EmailService emailService;

    @Autowired
    ApplicationContext context;

    public void serviceRequestFormSubmit(MockHttpServletRequest request){
        ServiceRequest serviceRequest = this.context.getBean(ServiceRequest.class);
        serviceRequest.setCustomerByCustomerNo(request.getParameter("custNo"));
        this.addNewServiceRequest(serviceRequest);
    }

    public void addNewServiceRequest(ServiceRequest serviceRequest){
        this.serviceRequestDao.add(serviceRequest);
        // this.emailService.sendEmail(serviceRequest.getCustomer().getEmail(), "A/S 접수가 정상적으로 처리되었습니다.");
        // 구체적인 구현방법은 serviceRequest 객체에서 담당
        serviceRequest.notifyServiceRequestRegistration();
    }

}
