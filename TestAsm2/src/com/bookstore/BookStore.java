package com.bookstore;

import java.util.*;

public class BookStore {
    private Map<Integer, Book> bookInventory;
    private OrderQueue orderQueue;
    private int nextBookID;

    public BookStore() {
        bookInventory = new HashMap<>();
        orderQueue = new OrderQueue();
        nextBookID = 1;  // Initialize book ID counter
    }

    public void addBook(int bookID, String title, String author, double price, int quantity) {
        if (bookInventory.containsKey(bookID)) {
            System.out.println("Book ID already exists. Please use a different ID.");
            return;
        }
        Book book = new Book(bookID, title, author, price, quantity);
        bookInventory.put(bookID, book);
        System.out.println("Book added " +book );
    }


    public void addBook(String title, String author, double price, int quantity) {
        Book book = new Book(nextBookID++, title, author, price, quantity);
        bookInventory.put(book.getId(), book);
        System.out.println("Book added " +book);
    }

    public Book getBookByID(int id) {
        return bookInventory.get(id);
    }

    public void displayAllBooks() {
        System.out.println("\n--- ALL BOOKS ---");
        System.out.printf("%-10s %-30s %-30s %-10s %-10s%n", "ID", "Title", "Author", "Price", "Quantity");
        System.out.println("-------------------------------------------------------------");
        for (Book book : bookInventory.values()) {
            System.out.printf("%-10d %-30s %-30s %-10.2f %-10d%n",
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getPrice(),
                    book.getQuantity());
        }
    }

    public void searchBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Search by (1) Title or (2) Author?");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline character

        if (choice == 1) {
            System.out.print("Enter book title to search: ");
            String title = scanner.nextLine();
            searchBooksByTitle(title);
        } else if (choice == 2) {
            System.out.print("Enter author name to search: ");
            String author = scanner.nextLine();
            searchBooksByAuthor(author);
        } else {
            System.out.println("Invalid choice.");
        }
    }

    public void searchBooksByTitle(String title) {
        System.out.println("\n--- SEARCH RESULTS ---");
        System.out.printf("%-10s %-30s %-30s %-10s %-10s%n", "ID", "Title", "Author", "Price", "Quantity");
        System.out.println("-------------------------------------------------------------");

        boolean found = false;
        for (Book book : bookInventory.values()) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                System.out.printf("%-10d %-30s %-30s %-10.2f %-10d%n",
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getPrice(),
                        book.getQuantity());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books found with the title: " + title);
        }
    }


    public void searchBooksByAuthor(String author) {
        System.out.println("\n--- SEARCH RESULTS ---");
        System.out.printf("%-10s %-30s %-30s %-10s %-10s%n", "ID", "Title", "Author", "Price", "Quantity");
        System.out.println("-------------------------------------------------------------");

        boolean found = false;
        for (Book book : bookInventory.values()) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                System.out.printf("%-10d %-30s %-30s %-10.2f %-10d%n",
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getPrice(),
                        book.getQuantity());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books found by the author: " + author);
        }
    }


    public void sortBooks() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Sort books by (1) Title or (2) Author?");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline character

        List<Book> books = new ArrayList<>(bookInventory.values());

        if (choice == 1) {
            SortAlgorithms.insertionSort(books, "title");
            System.out.println("\n--- BOOKS SORTED BY TITLE ---");
        } else if (choice == 2) {
            SortAlgorithms.insertionSort(books, "author");
            System.out.println("\n--- BOOKS SORTED BY AUTHOR ---");
        } else {
            System.out.println("Invalid choice.");
            return;
        }

        // Print the sorted books
        System.out.printf("%-10s %-30s %-30s %-10s %-10s%n", "ID", "Title", "Author", "Price", "Quantity");
        System.out.println("-------------------------------------------------------------");
        for (Book book : books) {
            System.out.printf("%-10d %-30s %-30s %-10.2f %-10d%n",
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getPrice(),
                    book.getQuantity());
        }
    }

    public void addOrder(Order order) {
        orderQueue.addOrder(order);
    }

    public Order searchOrder(int orderID) {
        return orderQueue.searchOrder(orderID);
    }

    public OrderQueue getOrderQueue() {
        return orderQueue;
    }

    public void processOrder(Order order) {
        for (Map.Entry<Book, Integer> entry : order.getBookListWithQuantities().entrySet()) {
            Book book = entry.getKey();
            int quantityOrdered = entry.getValue();
            book.setQuantity(book.getQuantity() - quantityOrdered);
        }

    }


    public void updateBookQuantity(int bookID, int newQuantity) {
        Book book = bookInventory.get(bookID);
        if (book != null) {
            book.setQuantity(newQuantity);
        } else {
            System.out.println("Book with ID " + bookID + " not found.");
        }
    }
    public void processNextOrder() {
        Order order = orderQueue.poll();  // Lấy và loại bỏ phần tử đầu tiên trong hàng đợi
        if (order != null) {  // Kiểm tra nếu còn đơn hàng trong hàng đợi
            if (!order.isProcessed()) {
                processOrder(order);
                order.setProcessed(true);
                System.out.println("Order ID " + order.getOrderID() + " has been processed.");
            } else {
                System.out.println("Order ID " + order.getOrderID() + " is already processed.");
            }
        } else {
            System.out.println("No more orders to process.");
        }
    }
}
