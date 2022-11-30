package springbook.user.service;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionAdvice implements MethodInterceptor {

    private PlatformTransactionManager transactionManager; // 트랜잭션 기능을 제공하는데 필요한 트랜잭션 매니저

    public void setTransactionManager(PlatformTransactionManager transactionManager){
        this.transactionManager=transactionManager;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());

        try{
            Object ret = invocation.proceed();
            this.transactionManager.commit(status);
            return ret;

        }catch (RuntimeException e){
            this.transactionManager.rollback(status);
            throw e;
        }

    }

}
