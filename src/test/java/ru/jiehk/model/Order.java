package ru.jiehk.model;

import java.util.List;

public class Order {
    public int orderID;
    public double orderPrice;
    public String orderDate;
    public boolean isDeliveryFree;
    public List<OrderItems> orderItems;

    public static class OrderItems {
        public int productID;
        public double amount;
        public double price;
    }

}