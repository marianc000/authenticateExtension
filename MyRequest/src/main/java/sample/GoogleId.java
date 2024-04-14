package sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import org.springframework.stereotype.Component;

@Component
public class GoogleId {

    String TOKEN_INFO_URL = "https://oauth2.googleapis.com/tokeninfo?";

    String get(String url) throws IOException, InterruptedException {
        var req = HttpRequest.newBuilder().uri(URI.create(url)).build();
        var res = HttpClient.newHttpClient().send(req, BodyHandlers.ofString());

        if (res.statusCode() >= 400) {
            throw new RuntimeException("Status code: " + res.statusCode());
        }
        return res.body();
    }

    public String userIdFromToken(String parameter) {
        try {
            String r = get(TOKEN_INFO_URL + parameter);
            System.out.println(r);
            return  new ObjectMapper().readTree(r).get("email").asText() ;
        } catch (Exception ex) {
            return null;
        }
    }
}
