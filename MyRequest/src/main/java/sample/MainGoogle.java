package sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.function.UnaryOperator;
 
public class MainGoogle {

    String USER_INFO_URL = "https://www.googleapis.com/oauth2/v2/userinfo";
    String TOKEN_INFO_URL = "https://oauth2.googleapis.com/tokeninfo?";

    static String accessToken = "ya29.a0Ad52N3-V_E6Po1QJ7ueARGY89woqYm1q-Lbg8lukY-c50alifuQsxSAx7rUwDEWqTYzL61Xha-PRt-NF6RuOnMtc2yvA6Vam1T75Ruoh2-eoP7vjK0TNEbQyqmXCJWdRcCxqenpwBxyGckIQawDvnlWYyAp_O3lrywaCgYKAa4SARASFQHGX2Mi2oucnWQjMKs7wPOgsfX0Zw0169" ;
    static String idToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjkzYjQ5NTE2MmFmMGM4N2NjN2E1MTY4NjI5NDA5NzA0MGRhZjNiNDMiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI0MTI1NTIxNDA0NzgtNDdnYXM5ZjNoMWN2NmZqbDYwbzN0c2tpY3Q5ZzU2cTYuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI0MTI1NTIxNDA0NzgtNDdnYXM5ZjNoMWN2NmZqbDYwbzN0c2tpY3Q5ZzU2cTYuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMTY4NjczMDQ5NzEyODQ3OTg1NjYiLCJlbWFpbCI6Im1hcmlhbi5jYWlrb3Zza2lAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImF0X2hhc2giOiJyYlAzMjdCd1draDBWVEpBRjBYTF9RIiwibm9uY2UiOiJhbnkiLCJuYmYiOjE3MTI2MDI1MTUsImlhdCI6MTcxMjYwMjgxNSwiZXhwIjoxNzEyNjA2NDE1LCJqdGkiOiJjODBiMTc1MDYyMDViMTdlNmIxZTZjYzYwYjRkMTMzNDA2ODg4Y2Q3In0.Jz3zBbVMI2l9klSjQTCK_JM_QVPS8iywlITCvbtrewTxDgzC1KCb3N-bsxUip3xFroT-IcOb6u5G7kJWZwmvtqEytohoWVXdf7i7Eert7XuvgMOEy5b8J2aKmmpYjKzqZaqyyKmH4LlKTsXCpwCsm0--0x1XSR9EdNK0uptkgGeber3DzdWdSxvZuCzDmgR5qAFGg6wFwlGVRLyd7n-IWLWMV3VEjJLtZwHnkIdasyLFFVZ6koZbBuA6xYeof-yr7mmxiXa1-1RUzcragNa6puDZEbe7YrMUBtw3rp2s9V5_fhUQ-COQ63LlwtIq5JRMgRM8RO19k9t3pUPE4wxzLg";
    HttpClient client = HttpClient.newHttpClient();

    UnaryOperator<HttpRequest.Builder> headers = b -> {
        b.setHeader("Authorization", "Bearer " + accessToken);
        return b;
    };

    public String get(String url, UnaryOperator<HttpRequest.Builder> headers) throws IOException, InterruptedException {
        System.out.println(">get: " + url);

        var request = HttpRequest.newBuilder().uri(URI.create(url));

        if (headers != null) {
            request = headers.apply(request);
        }

        var res = client.send(request.build(), BodyHandlers.ofString());
        System.out.println(">response code: " + res.statusCode());
        if (res.statusCode() >= 400) {
            throw new RuntimeException("error: " + res.statusCode());
        }
        return res.body();
    }
    static String EMAIL = "email";

    void run(String accessToken, String idToken) throws IOException, InterruptedException {
        System.out.println(get(USER_INFO_URL, b -> b.setHeader("Authorization", "Bearer " + accessToken)));

        var om = new ObjectMapper();
        String r = get(TOKEN_INFO_URL + "access_token=" + accessToken, null);
        System.out.println(r);
        String email = om.readTree(r).path(EMAIL).asText();
        System.out.println(">email: " + email);

        r = get(TOKEN_INFO_URL + "id_token=" + idToken, null);
        System.out.println(r);
        email = om.readTree(r).path(EMAIL).asText();
        System.out.println(">email: " + email);
    }

    public static void main(String... args) throws IOException, InterruptedException {
        new MainGoogle().run(accessToken, idToken);
    }
}
