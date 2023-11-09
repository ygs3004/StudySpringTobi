package springbook.user.domain;

public enum Level {

    GOLD(3, null), SILVER(2, GOLD), BASIC(1, SILVER); //3개의 이늄 오브젝트 정의
    // SILVE : 로그인 50회, GOLD : 실버이면서 추천수 30

    private final int value;
    private final Level next; // 다음 단계의 레벨을 스스로 갖고있기

    Level(int value, Level next){
        this.value = value;
        this.next = next;
    }

    public int intValue() {
        return value;
    }

    public Level nextLevel(){
        return this.next;
    }

    public static Level valueOf(int value) {  // 값으로부터 Level타입 오브젝트를 가져오도록 만든 스태틱 메소드

        switch (value) {
            case 1: return BASIC;
            case 2: return SILVER;
            case 3: return GOLD;
            default: throw new AssertionError("Unknown value : " + value);
        }

    }

}
