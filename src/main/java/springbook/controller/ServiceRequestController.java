package springbook.controller;

import jakarta.inject.Inject;
import jakarta.inject.Provider;
import org.springframework.mock.web.MockHttpServletRequest;
import springbook.dao.CustomerDao;
import springbook.dao.ServiceRequestDao;
import springbook.dto.ServiceRequest;

public class ServiceRequestController {

    CustomerDao customerDao;
    ServiceRequestDao serviceRequestDao;

    @Inject
    Provider<ServiceRequest> serviceRequestProvider;

    public void serviceRequestFormSubmit(MockHttpServletRequest request){
        ServiceRequest serviceRequest = this.serviceRequestProvider.get();
        // serviceRequest.setCustomerByCustomerNo(request.getParameter("custNo"));
        this.addNewServiceRequest(serviceRequest);
    }

    public void addNewServiceRequest(ServiceRequest serviceRequest){
        this.serviceRequestDao.add(serviceRequest);
        serviceRequest.notifyServiceRequestRegistration();
    }

}
