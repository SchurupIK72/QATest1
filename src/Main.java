import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import java.util.logging.*;

public class Main {

    static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final String API_KEY = "special-key";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception {
        setupLogger();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nChoose an action:");
            System.out.println("1 - Get pet by ID (GET)");
            System.out.println("2 - Add new pet (POST)");
            System.out.println("3 - Update existing pet (PUT)");
            System.out.println("4 - Delete pet by ID (DELETE)");
            System.out.println("0 - Exit");
            System.out.print("Your choice: ");

            int choice = readInt(scanner, "");  // now also uses safe input

            switch (choice) {
                case 1 -> {
                    long petId = readLong(scanner, "Enter pet ID: ");
                    getPetById(petId);
                }
                case 2 -> {
                    long id = readLong(scanner, "Enter new pet ID: ");
                    String name = readString(scanner, "Enter pet name: ");
                    String status = readString(scanner, "Enter status (available, pending, sold): ");
                    createPet(id, name, status);
                }
                case 3 -> {
                    long id = readLong(scanner, "Enter ID of the pet to update: ");
                    String name = readString(scanner, "Enter new name: ");
                    String status = readString(scanner, "Enter new status: ");
                    updatePet(id, name, status);
                }
                case 4 -> {
                    long idToDelete = readLong(scanner, "Enter pet ID to delete: ");
                    deletePet(idToDelete);
                }
                case 0 -> {
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    public static void getPetById(long petId) {
        String endpoint = "/pet/" + petId;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .GET()
                .build();

        sendRequest("GET " + endpoint, request);
    }

    public static void createPet(long id, String name, String status) {
        String json = generatePetJson(id, name, status);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/pet"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        sendRequest("POST /pet", request);
    }

    public static void updatePet(long id, String name, String status) {
        String json = generatePetJson(id, name, status);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/pet"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        sendRequest("PUT /pet", request);
    }

    public static void deletePet(long petId) {
        String endpoint = "/pet/" + petId;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("api_key", API_KEY)
                .DELETE()
                .build();

        sendRequest("DELETE " + endpoint, request);
    }

    private static String generatePetJson(long id, String name, String status) {
        return String.format("""
                {
                  "id": %d,
                  "name": "%s",
                  "photoUrls": ["http://example.com/photo.jpg"],
                  "status": "%s"
                }
                """, id, name, status);
    }

    private static void sendRequest(String label, HttpRequest request) {
        try {
            logger.info("Sending request: " + label);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            logger.info("Response status: " + response.statusCode());
            logger.info("Response body: " + response.body());

            System.out.println("\n" + label);
            System.out.println("Status: " + response.statusCode());
            System.out.println("Response:\n" + response.body());

        } catch (IOException | InterruptedException e) {
            logger.log(Level.SEVERE, "Request failed: " + label, e);
            System.out.println("An error occurred. Check logs for details.");
        }
    }

    private static long readLong(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextLong()) {
                long value = scanner.nextLong();
                scanner.nextLine(); // clear leftover newline
                return value;
            } else {
                System.out.println("Please enter a valid number.");
                scanner.next(); // discard invalid input
            }
        }
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine(); // clear leftover newline
                return value;
            } else {
                System.out.println("Please enter a valid integer.");
                scanner.next(); // discard invalid input
            }
        }
    }

    private static String readString(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static void setupLogger() {
        try {
            LogManager.getLogManager().reset();

            // Console handler
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.INFO);
            logger.addHandler(consoleHandler);

            // File handler
            FileHandler fileHandler = new FileHandler("logs.txt", true);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);

            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            System.out.println("Failed to initialize logger: " + e.getMessage());
        }
    }
}