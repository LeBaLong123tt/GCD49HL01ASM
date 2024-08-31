package com.bookstore;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Order {
    private int orderID;
    private Customer customer;
    private Map<Book, Integer> bookListWithQuantities;
    private boolean isProcessed;

    public Order(int orderID, Customer customer, Map<Book, Integer> bookListWithQuantities) {
        this.orderID = orderID;
        this.customer = customer;
        this.bookListWithQuantities = bookListWithQuantities;
        this.isProcessed = false;
    }

    public int getOrderID() {
        return orderID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Map<Book, Integer> getBookListWithQuantities() {
        return bookListWithQuantities;
    }

    public boolean isProcessed() {
        return isProcessed;
    }

    public void setProcessed(boolean isProcessed) {
        this.isProcessed = isProcessed;
    }

    public void sortBooks() {
        List<Book> books = new ArrayList<>(bookListWithQuantities.keySet());
        SortAlgorithms.insertionSort(books, "title");
        Map<Book, Integer> sortedBooks = new LinkedHashMap<>();
        for (Book book : books) {
            sortedBooks.put(book, bookListWithQuantities.get(book));
        }
        this.bookListWithQuantities = sortedBooks;
    }


    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", customer=" + customer +
                ", bookListWithQuantities=" + bookListWithQuantities +
                ", isProcessed=" + isProcessed +
                '}';
    }
}
