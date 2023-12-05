package springbook.learningtest.spring.ioc.dto;

public enum NotificationMethod {
    EMAIL(1);
    final private int notificationMethod;

    NotificationMethod(int notificationMethod){
        this.notificationMethod = notificationMethod;
    }
}
