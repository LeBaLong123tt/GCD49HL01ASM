package com.bookstore;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.InputMismatchException;
public class Main {
    private static int orderIDCounter = 1;  // Counter for unique order IDs

    public static void main(String[] args) {
        BookStore bookStore = new BookStore();
        Scanner scanner = new Scanner(System.in);

        // Add books to the store
        bookStore.addBook(1, "Doraemon", "FF", 29.99, 4);
        bookStore.addBook(2, "Naruto", "DD", 39.99, 5);
        bookStore.addBook(3, "ORV", "Sing Pong", 19.99, 6);

        while (true) {
            // Display the main menu
            System.out.println("\n=====================");
            System.out.println("      MAIN MENU");
            System.out.println("=====================");
            System.out.println("1. Add Book to Inventory");
            System.out.println("2. Place Order");
            System.out.println("3. Search for Order");
            System.out.println("4. Display All Order IDs");
            System.out.println("5. Display All Books");
            System.out.println("6. Search for Books");
            System.out.println("7. Sort Books");
            System.out.println("8. Process Orders");
            System.out.println("9. Exit");
            System.out.print("Select an option: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline character
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice. Please enter a number between 1 and 9.");
                scanner.nextLine();  // Xóa bộ đệm
                continue;  // Tiếp tục vòng lặp
            }
            switch (choice) {
                case 1:

                    // Existing code for adding books
                    System.out.println("\n--- ADD BOOK ---");
                    System.out.print("Enter Book ID: ");
                    int bookID = scanner.nextInt();
                    scanner.nextLine();  // Consume newline character
                    System.out.print("Enter Book Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Author Name: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter Book Price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Enter Quantity: ");
                    int quantity = scanner.nextInt();
                    bookStore.addBook(bookID, title, author, price, quantity);
                    break;


                case 2:
                    System.out.println("\n--- PLACE ORDER ---");
                    System.out.print("Enter Customer Name: ");
                    String customerName = scanner.nextLine();
                    System.out.print("Enter Shipping Address: ");
                    String address = scanner.nextLine();
                    Customer customer = new Customer(customerName, address);

                    Map<Book, Integer> orderBooks = new HashMap<>();
                    boolean continueOrdering = true;
                    int totalQuantity = 0;

                    while (continueOrdering) {
                        System.out.print("Enter Book ID: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();  // Consume newline character

                        Book book = bookStore.getBookByID(id);
                        if (book != null) {
                            System.out.print("Enter Quantity for Book ID " + id + ": ");
                            int qty = scanner.nextInt();
                            scanner.nextLine();  // Consume newline character

                            if (qty > 0 && qty <= book.getQuantity()) {
                                orderBooks.put(book, qty);
                                totalQuantity += qty;
                                bookStore.updateBookQuantity(id, book.getQuantity() - qty); // Update book quantity in store
                            } else {
                                System.out.println("Invalid quantity for Book ID " + id + ". It must be between 1 and " + book.getQuantity());
                                continue;  // Prompt for book ID and quantity again
                            }
                        } else {
                            System.out.println("No book found with ID: " + id);
                            continue;  // Prompt for book ID and quantity again
                        }

                        System.out.print("Do you want to order more books? (yes/no): ");
                        String response = scanner.nextLine();
                        if (response.equalsIgnoreCase("no")) {
                            continueOrdering = false;
                        }
                    }

                    int orderID = orderIDCounter++;  // Create a unique order ID
                    Order order = new Order(orderID, customer, orderBooks);
                    bookStore.addOrder(order);
                    System.out.println("Order has been successfully placed!");
                    System.out.println("Total Books Ordered: " + totalQuantity);
                    System.out.println("Order ID: " + orderID);

                    break;

                case 3:
                    // Existing code for searching orders
                    System.out.println("\n--- SEARCH FOR ORDER ---");
                    System.out.print("Enter Order ID to search: ");
                    int searchOrderID = scanner.nextInt();
                    scanner.nextLine();  // Consume newline character
                    Order searchedOrder = bookStore.searchOrder(searchOrderID);
                    if (searchedOrder != null) {
                        System.out.println("Order found: ");
                        System.out.println("Order ID: " + searchedOrder.getOrderID());
                        System.out.println("Customer Name: " + searchedOrder.getCustomer().getName());
                        System.out.println("Customer Address: " + searchedOrder.getCustomer().getAddress());
                        System.out.println("Books Ordered:");
                        for (Map.Entry<Book, Integer> entry : searchedOrder.getBookListWithQuantities().entrySet()) {
                            Book book = entry.getKey();
                            int qty = entry.getValue();
                            System.out.println("Book Title: " + book.getTitle() + ", Quantity: " + qty);
                        }
                    } else {
                        System.out.println("Order not found.");
                    }

                    break;

                case 4:
                    // Existing code for displaying order IDs
                    System.out.println("\n--- ALL ORDER IDS ---");
                    OrderQueue queue = bookStore.getOrderQueue();
                    queue.displayOrderIDs();

                    break;

                case 5:
                    // Existing code for displaying all books
                    System.out.println("\n--- ALL BOOKS ---");
                    bookStore.displayAllBooks();

                    break;

                case 6:
                    // Existing code for searching books
                    System.out.println("\n--- SEARCH FOR BOOKS ---");
                    bookStore.searchBook();  // Search for books by title or author

                    break;

                case 7:
                    // Existing code for sorting books
                    System.out.println("\n--- SORT BOOKS ---");
                    bookStore.sortBooks();

                    break;
                case 8:

                    System.out.println("\n--- PROCESS ALL ORDERS ---");
                    bookStore.processNextOrder();
                    long startTimeAdd = System.nanoTime();
                    long endTimeAdd = System.nanoTime();
                    long durationAdd = endTimeAdd - startTimeAdd; // Thời gian thực thi trong nano giây
                    System.out.println("Time to PROCESS ORDER : " + durationAdd + " nano time");
                    break;

                case 9:
                    // Exit the application
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice, please try again.");
            }

        }


    }
}
