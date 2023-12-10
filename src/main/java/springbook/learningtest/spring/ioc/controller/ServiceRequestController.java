package springbook.learningtest.spring.ioc.controller;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import springbook.learningtest.spring.ioc.dao.CustomerDao;
import springbook.learningtest.spring.ioc.dao.ServiceRequestDao;
import springbook.learningtest.spring.ioc.dto.ServiceRequest;

import javax.annotation.Resource;

public class ServiceRequestController {

    CustomerDao customerDao;
    ServiceRequestDao serviceRequestDao;

    @Resource
    private ObjectFactory<ServiceRequest> serviceRequestFactory;

    public void serviceRequestFormSubmit(MockHttpServletRequest request){
        ServiceRequest serviceRequest = this.serviceRequestFactory.getObject();
        serviceRequest.setCustomerByCustomerNo(request.getParameter("custNo"));
        this.addNewServiceRequest(serviceRequest);
    }

    public void addNewServiceRequest(ServiceRequest serviceRequest){
        this.serviceRequestDao.add(serviceRequest);
        serviceRequest.notifyServiceRequestRegistration();
    }

}
