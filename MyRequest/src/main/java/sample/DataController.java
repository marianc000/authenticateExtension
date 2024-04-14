package sample;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    @Autowired
    UserRepository userRepo;

    @RequestMapping("/data")
    public Data data(@AuthenticationPrincipal User user, HttpSession session) {

        String sessionId = session.getId();

        if (userRepo.useCredits(user, 1)) {
            return new Data(user, sessionId, System.currentTimeMillis());
        }

        return new Data(user, sessionId);
    }
}
