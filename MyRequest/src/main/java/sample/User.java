package sample;

public class User {

    String email;
    int credits;

    public User(String email) {
        this.email = email;
    }

    public int getCredits() {
        return credits;
    }
 
    public void setCredits(int credits) {
        this.credits = credits;
    }

    @Override
    public String toString() {
        return "User{" + "email=" + email + '}';
    }
}
