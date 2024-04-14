package sample;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    public User findOrCreateById(String id) {
        User user = new User(id);
        user.setCredits(5);
        return user;
    }

    public boolean useCredits(User user, int cost) {
        int remainder = user.getCredits() - cost;
        
        if (remainder < 0) {
            return false;
        }
        
        user.setCredits(remainder);
        return true;
    }
}
