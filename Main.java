import api.ApiClient;
import api.PetService;
import ui.ConsoleUI;
import util.LoggerConfig;

import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger logger = LoggerConfig.setupLogger(Main.class.getName());
        ApiClient client = new ApiClient(logger);
        PetService petService = new PetService(client);
        ConsoleUI ui = new ConsoleUI();

        while (true) {
            int choice = ui.showMenuAndGetChoice();
            try {
                switch (choice) {
                    case 1 -> petService.getPetById(ui.readLong("Enter pet ID: "));
                    case 2 -> petService.createPet(
                            ui.readLong("Enter new pet ID: "),
                            ui.readString("Enter pet name: "),
                            ui.readString("Enter status (available, pending, sold): "));
                    case 3 -> petService.updatePet(
                            ui.readLong("Enter pet ID: "),
                            ui.readString("Enter new name: "),
                            ui.readString("Enter new status: "));
                    case 4 -> petService.deletePet(ui.readLong("Enter pet ID to delete: "));
                    case 0 -> {
                        ui.close();
                        System.out.println("Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                logger.severe("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}