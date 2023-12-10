package springbook.learningtest.spring.ioc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import springbook.learningtest.spring.ioc.dao.CustomerDao;
import springbook.learningtest.spring.ioc.dao.ServiceRequestDao;
import springbook.learningtest.spring.ioc.dto.ServiceRequest;
import springbook.learningtest.spring.ioc.factory.ServiceRequestFactory;

public abstract class ServiceRequestController {

    CustomerDao customerDao;
    ServiceRequestDao serviceRequestDao;

    abstract public ServiceRequest getServiceRequest();

    @Autowired
    private ServiceRequestFactory serviceRequestFactory;

    public void serviceRequestFormSubmit(MockHttpServletRequest request){
        ServiceRequest serviceRequest = this.getServiceRequest();
        serviceRequest.setCustomerByCustomerNo(request.getParameter("custNo"));
        this.addNewServiceRequest(serviceRequest);
    }

    public void addNewServiceRequest(ServiceRequest serviceRequest){
        this.serviceRequestDao.add(serviceRequest);
        serviceRequest.notifyServiceRequestRegistration();
    }

}
