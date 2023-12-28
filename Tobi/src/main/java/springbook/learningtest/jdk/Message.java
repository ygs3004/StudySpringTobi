package springbook.learningtest.jdk;

public class Message {

    String text;

    //private으로 선언되어 외부에서 생성자 호출 안댐
    private Message(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }

    // 생성자 대신 사용할 수 있는 스태틱 메소드
    public static Message newMessage(String text){
        return new Message(text);
    }
}
