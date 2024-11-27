package MushroomIndex;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Scanner;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MushroomManager manager = new MushroomManager();
        Scanner scanner = new Scanner(System.in);

        manager.loadMushroomsFromFile();
        
        boolean running = true;

        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. List Mushrooms");
            System.out.println("2. Add Mushroom");
            System.out.println("3. Remove Mushroom");
            System.out.println("4. Save Mushrooms");
            System.out.println("5. Interact with Mushroom");
            System.out.println("6. Undo Last Action");
            System.out.println("7. Redo Last Action");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume leftover newline

                switch (choice) {
                    case 1 -> manager.listMushrooms();
                    case 2 -> {
                        System.out.print("Enter mushroom name: ");
                        String name = scanner.nextLine();
                        System.out.print("Is it toxic? (yes/no): ");
                        String toxic = scanner.nextLine();
                        if (toxic.equalsIgnoreCase("yes")) {
                            System.out.print("Enter toxicity level: ");
                            String toxicityLevel = scanner.nextLine();
                            manager.addMushroom(new Toxicity(name, toxicityLevel));
                        } else {
                            System.out.print("Is it gourmet? (yes/no): ");
                            boolean isGourmet = scanner.nextLine().equalsIgnoreCase("yes");
                            manager.addMushroom(new Edibility(name, isGourmet));
                        }
                        System.out.println("Mushroom added.");
                    }
                    case 3 -> {
                        System.out.print("Enter mushroom name to remove: ");
                        String name = scanner.nextLine();
                        manager.removeMushroom(name);
                    }
                    case 4 -> manager.saveMushroomsToFile();
                    case 5 -> {
                        System.out.print("Enter mushroom name to interact with: ");
                        String name = scanner.nextLine();
                        manager.interactWithMushroom(name);
                    
                    }
                    case 6 -> manager.undo();
                    case 7 -> manager.redo();
                    case 8 -> {
                        System.out.println("Exiting program.");
                        running = false;
                    }
                    default -> System.out.println("Invalid option. Try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }

        scanner.close();
    }
}



