package sample;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(1)
public class GoogleTokenFilter extends HttpFilter {

    @Autowired
    GoogleId google;

    @Autowired
    UserRepository userRepo;

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println(">doFilter " + req.getQueryString());
        if (req.getQueryString() != null) {
            String id = google.userIdFromToken(req.getQueryString());

            if (id != null) {
                User user = userRepo.findOrCreateById(id);
                var authentication = new UsernamePasswordAuthenticationToken(user, null, List.of());
                req.getSession().setAttribute("SPRING_SECURITY_CONTEXT", new SecurityContextImpl(authentication));
            }
        }
        chain.doFilter(req, res);
    }
}
