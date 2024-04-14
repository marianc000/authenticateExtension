package sample;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

//@Component
public class GoogleTokenFilter1  extends OncePerRequestFilter {
 
    @Autowired
    GoogleId google;

    @Autowired
    UserRepository userRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        System.out.println(">doFilterInternal"); 
        if (req.getQueryString() != null) {
            String id = google.userIdFromToken(req.getQueryString());

            if (id != null) {
                User user = userRepo.findOrCreateById(id);
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, List.of()));
            }
        }
        chain.doFilter(req, res);
    }
}
