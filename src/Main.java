import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<MenuItem> menu = new ArrayList<>();
        Order currentOrder = new Order();

        // Initialize the hardcoded menu
        initializeMenu(menu);

        boolean running = true;
        while (running) {
            System.out.println("\n========== Java Cafe ==========");
            System.out.println("Minimum order is $150 per person!");
            System.out.println("1. View Menu");
            System.out.println("2. Order Items");
            System.out.println("3. Cancel Items");
            System.out.println("4. Checkout");
            System.out.println("0. Exit System");
            System.out.print("Please enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    displayMenu(menu);
                    break;

                case "2":
                    orderItem(scanner, menu, currentOrder);
                    break;

                case "3":
                    cancelItem(scanner, currentOrder);
                    break;

                case "4":
                    boolean isCheckedOut = checkout(scanner, currentOrder);
                    // If checkout is successful, clear the cart and prepare for the next customer
                    if (isCheckedOut) {
                        currentOrder.clear();
                        System.out.println("\nPreparing for the next customer...");
                    }
                    break;

                case "0":
                    System.out.println("Thank you for using the system. The system is now closed!");
                    running = false;
                    break;

                default:
                    // Prevent inputs other than 0~4
                    System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        scanner.close();
    }

    private static void initializeMenu(ArrayList<MenuItem> menu) {
        menu.add(new MenuItem("F01", "Basque Cheesecake", 120));
        menu.add(new MenuItem("F02", "Original Croissant", 80));
        menu.add(new MenuItem("F03", "Tiramisu", 130));

        // Use the default constructor when creating beverages
        menu.add(new Beverage("D01", "Americano", 140));
        menu.add(new Beverage("D02", "Cafe Latte", 150));
        menu.add(new Beverage("D03", "Earl Grey Tea", 120));
    }

    private static void displayMenu(ArrayList<MenuItem> menu) {
        System.out.println("\n=== Today's Menu ===");
        System.out.println();

        // Table header row
        System.out.printf("%-5s %-20s %-7s %s%n", "ID", "Item Name", "Price", "Customization");
        // Call getDetails() and print the menu content
        for (MenuItem item : menu) {
            System.out.println(item.getDetails());
        }
    }

    private static void orderItem(Scanner scanner, ArrayList<MenuItem> menu, Order currentOrder) {
        displayMenu(menu);
        System.out.print("Enter item ID (or 's' to return): ");
        String id = scanner.nextLine().toUpperCase(); // Convert to uppercase for error prevention

        if (id.equals("S")) return;

        MenuItem selectedItem = null;
        for (MenuItem item : menu) {
            if (item.getId().equals(id)) {
                selectedItem = item;
                break;
            }
        }

        if (selectedItem == null) {
            System.out.println("Item ID not found!");
            return;
        }

        // If it's a beverage, instantiate a new Beverage object for customization to avoid modifying the original menu
        // Check if selectedItem is an instance of Beverage; if not, go to the else block
        if (selectedItem instanceof Beverage b) {
            System.out.print("Enter sugar level (Regular/Half/Low/None): ");
            String sugar = scanner.nextLine();
            System.out.print("Enter ice level (Regular/Less/Low/None): ");
            String ice = scanner.nextLine();

            Beverage customDrink = new Beverage(b.getId(), b.getName(), b.getPrice());
            customDrink.setCustomization(sugar, ice);
            currentOrder.addItem(customDrink);
        } else {
            // Add regular items directly to the cart
            currentOrder.addItem(selectedItem);
        }
    }

    private static void cancelItem(Scanner scanner, Order currentOrder) {
        if (currentOrder.getItems().isEmpty()) {
            System.out.println("The cart is currently empty!");
            return;
        }

        System.out.println("\n=== Current Cart ===");
        for (int i = 0; i < currentOrder.getItems().size(); i++) {
            System.out.println((i + 1) + ". " + currentOrder.getItems().get(i).getDetails());
        }
        System.out.print("Enter the sequence number to cancel (or '0' to return): ");

        try {
            int index = Integer.parseInt(scanner.nextLine());
            if (index == 0) return;
            // Array index starts at 0, so subtract 1
            currentOrder.removeItem(index - 1);
        } catch (NumberFormatException e) {
            // Catch exceptions where the user inputs a non-numeric string
            System.out.println("Please enter a valid number!");
        }
    }

    private static boolean checkout(Scanner scanner, Order currentOrder) {
        if (currentOrder.getItems().isEmpty()) {
            System.out.println("You haven't ordered any items yet!");
            return false;
        }

        int total = currentOrder.calculateTotal();
        System.out.println("\n========== Receipt ==========");
        for (MenuItem item : currentOrder.getItems()) {
            System.out.println(item.getDetails());
        }
        System.out.println();
        System.out.println("Original Total: $" + total);

        // Minimum order reminder
        if (total < 150) {
            System.out.println("Note: Minimum order of $150 per person has not been met!");
        }

        // 10% off for orders over $500
        int finalTotal = total;
        if (total >= 500) {
            finalTotal = (int) (total * 0.9);
            System.out.println("Orders over $500 receive a 10% discount!");
            System.out.println("Discounted Total: $" + finalTotal);
        } else {
            System.out.println("Total Due: $" + finalTotal);
        }
        System.out.println();

        // Payment and change
        while (true) {
            System.out.print("Enter payment amount: $");
            try {
                int payment = Integer.parseInt(scanner.nextLine());
                if (payment < finalTotal) {
                    System.out.println("Insufficient funds! You are short by $" + (finalTotal - payment) + ". Please re-enter.");
                } else {
                    int change = payment - finalTotal;
                    System.out.println("\nCheckout successful!");
                    System.out.println("Amount Received: $" + payment);
                    System.out.println("Change: $" + change);
                    System.out.println("Number of Items: " + currentOrder.getItems().size() + " items");
                    System.out.println("Thank you for visiting, please come again!");
                    return true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid numeric amount!");
            }
        }
    }
}
