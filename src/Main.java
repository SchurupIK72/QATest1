import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {

    static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final String API_KEY = "special-key";
    private static final HttpClient client = HttpClient.newHttpClient();

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose an action:");
        System.out.println("1 - Get pet by ID (GET)");
        System.out.println("2 - Add new pet (POST)");
        System.out.println("3 - Delete pet by ID (DELETE)");
        System.out.print("Your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // clear buffer

        switch (choice) {
            case 1 -> {
                System.out.print("Enter pet ID: ");
                long petId = scanner.nextLong();
                getPetById(petId);
            }
            case 2 -> {
                System.out.print("Enter new pet ID: ");
                long id = scanner.nextLong();
                scanner.nextLine(); // clear buffer

                System.out.print("Enter pet name: ");
                String name = scanner.nextLine();

                System.out.print("Enter status (available, pending, sold): ");
                String status = scanner.nextLine();

                createPet(id, name, status);
            }
            case 3 -> {
                System.out.print("Enter pet ID to delete: ");
                long idToDelete = scanner.nextLong();
                deletePet(idToDelete);
            }
            default -> System.out.println("Invalid choice.");
        }

        scanner.close();
    }

    public static void getPetById(long petId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/pet/" + petId))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("\nGET /pet/" + petId);
            System.out.println("Status: " + response.statusCode());
            System.out.println("Response:\n" + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
            System.out.println("Response:\n" + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
            System.out.println("Response:\n" + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}