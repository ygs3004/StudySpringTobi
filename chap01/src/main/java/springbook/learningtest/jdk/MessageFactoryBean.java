package springbook.learningtest.jdk;

import org.springframework.beans.factory.FactoryBean;

public class MessageFactoryBean implements FactoryBean<Message> {

    String text;

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public Message getObject() throws Exception {
        return Message.newMessage(this.text);
    }

    @Override
    public Class<?> getObjectType() {
        return Message.class;
    }

    @Override
    public boolean isSingleton() {
        return false; // 이 팩토리는 매번 요청시마다 새로운 오브젝트를 만드므로 false, 이것은 팩토리 빈의 동작 방식이고 빈오브트는 스프링이 싱글톤으로 관리가능
    }
}
