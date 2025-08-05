import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {

    static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final String API_KEY = "special-key"; // Swagger использует такой ключ по умолчанию


    public static void main(String[] args)throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        // -------- GET /pet/{petId} --------
        long petId = 1; // Попробуем получить питомца с ID = 1
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/pet/" + petId))
                .GET()
                .build();

        try {
            HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("GET /pet/" + petId);
            System.out.println("Status: " + getResponse.statusCode());
            System.out.println("Body:\n" + getResponse.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
               


        try {
            HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("GET /pet/" + petId);
            System.out.println("Status: " + getResponse.statusCode());
            System.out.println("Body:\n" + getResponse.body());
        } catch (Exception e) {
            e.printStackTrace();
        }



        // -------- POST /pet --------
        String newPetJson = """
                {
                  "id": 123456789,
                  "name": "Barsik",
                  "photoUrls": ["http://example.com/photo.jpg"],
                  "status": "available"
                }
                """;

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/pet"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(newPetJson))
                .build();

        try {
            HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("\nPOST /pet");
            System.out.println("Status: " + postResponse.statusCode());
            System.out.println("Body:\n" + postResponse.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
 long petIdToDelete = 123456789;
                  // DELETE /pet/{id}
        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/pet/" + petIdToDelete))
                .header("api_key", API_KEY)
                .DELETE()
                .build();

        HttpResponse<String> deleteResponse = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println("\nDELETE status: " + deleteResponse.statusCode());
        System.out.println("DELETE response: " + deleteResponse.body());

        try {
            HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("GET /pet/" + petIdToDelete);
            System.out.println("Status: " + getResponse.statusCode());
            System.out.println("Body:\n" + getResponse.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
               

    }
}

