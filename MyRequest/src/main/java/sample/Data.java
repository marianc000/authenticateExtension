package sample;

public class Data {

    User user;
    String sessionId;
    Long millis;

    public Data(User user, String sessionId, Long millis) {
        this(user, sessionId);
        this.millis = millis;
    }

    public Data(User user, String sessionId) {
        this.user = user;
        this.sessionId = sessionId;
    }

}
