/*
package springbook.user.service;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import java.lang.reflect.Proxy;

public class TxProxyFactoryBean implements FactoryBean<Object> {

    Object target;
    PlatformTransactionManager transactionManager;
    String pattern;
    Class<?> serviceInterface;

    public void setTarget(Object target) {
        this.target = target;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setServiceInterface(Class<?> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    //FactoryBean 인터페이스 구현 메소드
    @Override
    public Object getObject() throws Exception {
        TransactionAdvice txHandler = new TransactionAdvice();
        txHandler.setTarget(target);
        txHandler.setTransactionManager(transactionManager);
        txHandler.setPattern(pattern);
        return Proxy.newProxyInstance(
                getClass().getClassLoader(), new Class[]{serviceInterface}, txHandler
        );
    }

    @Override
    public Class<?> getObjectType() {
        return serviceInterface; //팩토리 빈이 생성하는 오브젝트 타잎은 DI 받은 인터페이스 타잎
    }

    @Override
    public boolean isSingleton() {
        return false; // 싱글톤 빈이 아니라는 뜻이 아니라 getObject가 매번 같은 오브젝트를 리턴하지 않는다는 의미
    }
}
*/
