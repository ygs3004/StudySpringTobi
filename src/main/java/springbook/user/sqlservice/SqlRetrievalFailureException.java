package springbook.user.sqlservice;

public class SqlRetrievalFailureException extends RuntimeException {

    public SqlRetrievalFailureException(String message) {
        super(message);
    }

    public SqlRetrievalFailureException(Throwable e) {
        super(e);
    }

    // Sql을 가져오는데 실패한 근본 원인을 담을 수 있도록 중첩 예외를 저장하는 생성자 
    public SqlRetrievalFailureException(String message, Throwable cause) {
        super(message, cause);
    }

}
