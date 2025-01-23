package com.example.event_db_connect.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class Payload implements Serializable{

    private String orderId;
    private String customerId;
    private List<Item> items;
    private double totalAmount;
    private String orderStatus;

    public static class Item implements Serializable {
        private String itemId;
        private String name;
        private int quantity;
        private double price;

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public Item() {

        }
        @JsonCreator
        public Item(
                @JsonProperty("itemId") String itemId,
                @JsonProperty("name") String name,
                @JsonProperty("quantity") int quantity,
                @JsonProperty("price") double price
        ) {
            this.itemId = itemId;
            this.name = name;
            this.quantity = quantity;
            this.price = price;
        }
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @JsonCreator
        public void Payload(
                @JsonProperty("orderId") String orderId,
                @JsonProperty("customerId") String customerId,
                @JsonProperty("items") List<Item> items,
                @JsonProperty("totalAmount") double totalAmount,
                @JsonProperty("orderStatus") String orderStatus
        ) {
            this.orderId = orderId;
            this.customerId = customerId;
            this.items = items;
            this.totalAmount = totalAmount;
            this.orderStatus = orderStatus;
        }

        // Getters and setters
    }



