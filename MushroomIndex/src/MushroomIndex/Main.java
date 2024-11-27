package MushroomIndex;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Scanner;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MushroomManager manager = new MushroomManager();
        Scanner scanner = new Scanner(System.in);

        manager.addMushroom(new Toxicity("Death Cap", "High"));
        manager.saveMushroomsToFile();

        manager.loadMushroomsFromFile();
        
        boolean running = true;

        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. List Mushrooms");
            System.out.println("2. Add Mushroom");
            System.out.println("3. Remove Mushroom");
            System.out.println("4. Save Mushrooms");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

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
                }
                case 3 -> {
                    System.out.print("Enter mushroom name to remove: ");
                    String name = scanner.nextLine();
                    manager.removeMushroom(name);
                }
                case 4 -> manager.saveMushroomsToFile();
                case 5 -> running = false;
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void addNewMushroom(MushroomManager manager, Scanner scanner) {
        System.out.println("Enter mushroom name:");
        String name = scanner.nextLine();

        System.out.println("Is the mushroom toxic? (yes/no)");
        String toxicResponse = scanner.nextLine().trim().toLowerCase();
        boolean isToxic = toxicResponse.equals("yes");

        if (isToxic) {
            System.out.println("Enter toxicity level (Low/Medium/High):");
            String toxicityLevel = scanner.nextLine();
            manager.addMushroom(new Toxicity(name, toxicityLevel));
        } else {
            System.out.println("Is it gourmet? (yes/no):");
            String gourmetResponse = scanner.nextLine().trim().toLowerCase();
            boolean isGourmet = gourmetResponse.equals("yes");
            manager.addMushroom(new Edibility(name, isGourmet));
        }

        System.out.println("Mushroom added successfully!");
    }
   
        
    }



