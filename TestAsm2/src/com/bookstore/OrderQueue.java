package com.bookstore;

import java.util.LinkedList;
import java.util.Queue;

public class OrderQueue {
    private Queue<Order> orderQueue;

    public OrderQueue() {
        orderQueue = new LinkedList<>();
    }

    public void addOrder(Order order) {
        orderQueue.add(order);

    }

    public Order searchOrder(int orderID) {

        for (Order order : orderQueue) {
            if (order.getOrderID() == orderID) {
                return order;
            }
        }
        return null;



    }
    public Order poll() {
        return orderQueue.poll();
    }

    public void displayOrderIDs() {
        System.out.println("Unprocessed Order IDs in Queue:");
        for (Order order : orderQueue) {
            if (!order.isProcessed()) {  // Check if the order is not processed
                System.out.println(order.getOrderID());
            }
        }

    }
}
