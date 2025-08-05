package api;

import java.io.IOException;
import java.net.http.*;
import java.util.logging.Logger;

public class ApiClient {
    private static final HttpClient client = HttpClient.newHttpClient();
    private final Logger logger;

    public ApiClient(Logger logger) {
        this.logger = logger;
    }

    public HttpResponse<String> send(HttpRequest request) throws IOException, InterruptedException {
        logger.info("Sending request: " + request.method() + " " + request.uri());
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info("Response: " + response.statusCode() + " " + response.body());
        return response;
    }
}