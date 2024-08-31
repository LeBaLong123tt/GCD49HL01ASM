package com.bookstore;

import java.util.List;

public class SortAlgorithms {
    public static void insertionSort(List<Book> books, String sortBy) {
        for (int i = 1; i < books.size(); i++) {
            Book key = books.get(i);
            int j = i - 1;
            while (j >= 0 && compare(books.get(j), key, sortBy) > 0) {
                books.set(j + 1, books.get(j));
                j = j - 1;
            }
            books.set(j + 1, key);
        }
    }

    private static int compare(Book b1, Book b2, String sortBy) {
        switch (sortBy.toLowerCase()) {
            case "title":
                return b1.getTitle().compareToIgnoreCase(b2.getTitle());
            case "author":
                return b1.getAuthor().compareToIgnoreCase(b2.getAuthor());
            default:
                return 0;
        }
    }
}
