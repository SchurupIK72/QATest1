package api;

import java.net.URI;
import java.net.http.HttpRequest;

public class PetService {
    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final String API_KEY = "special-key";
    private final ApiClient client;

    public PetService(ApiClient client) {
        this.client = client;
    }

    public void getPetById(long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/pet/" + id))
                .GET()
                .build();
        client.send(request);
    }

    public void createPet(long id, String name, String status) throws Exception {
        String json = generatePetJson(id, name, status);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/pet"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        client.send(request);
    }

    public void updatePet(long id, String name, String status) throws Exception {
        String json = generatePetJson(id, name, status);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/pet"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        client.send(request);
    }

    public void deletePet(long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/pet/" + id))
                .header("api_key", API_KEY)
                .DELETE()
                .build();
        client.send(request);
    }

    private String generatePetJson(long id, String name, String status) {
        return String.format("""
                {
                  "id": %d,
                  "name": "%s",
                  "photoUrls": ["http://example.com/photo.jpg"],
                  "status": "%s"
                }
                """, id, name, status);
    }
}