import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {

    static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final String API_KEY = "special-key"; // Swagger использует такой ключ по умолчанию

    private static final HttpClient client = HttpClient.newHttpClient();

    public static void main(String[] args) throws Exception {
        long petId = 1;
        long petIdToDelete = 123456789;

        // Выполняем операции
        getPetById(petId);

        createPet(123456789, "Barsik", "available");

        deletePet(petIdToDelete);

        getPetById(petIdToDelete); // Проверим, удалён ли
    }

    // -------- GET /pet/{petId} --------
    public static void getPetById(long petId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/pet/" + petId))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("\nGET /pet/" + petId);
            System.out.println("Status: " + response.statusCode());
            System.out.println("Body:\n" + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------- POST /pet --------
    public static void createPet(long id, String name, String status) {
        String json = String.format("""
                {
                  "id": %d,
                  "name": "%s",
                  "photoUrls": ["http://example.com/photo.jpg"],
                  "status": "%s"
                }
                """, id, name, status);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/pet"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("\nPOST /pet");
            System.out.println("Status: " + response.statusCode());
            System.out.println("Body:\n" + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------- DELETE /pet/{petId} --------
    public static void deletePet(long petId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/pet/" + petId))
                .header("api_key", API_KEY)
                .DELETE()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("\nDELETE /pet/" + petId);
            System.out.println("Status: " + response.statusCode());
            System.out.println("Body:\n" + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
               

    }
}   