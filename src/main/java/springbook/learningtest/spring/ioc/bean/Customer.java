package springbook.learningtest.spring.ioc.bean;

import springbook.learningtest.spring.ioc.dto.NotificationMethod;

public class Customer {

    private String email;
    public NotificationMethod serviceNotificationMethod;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
