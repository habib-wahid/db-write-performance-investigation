package com.example.event_db_connect.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;


public class EventDto implements Serializable {
    private String eventId;
    private String aggregateId;
    private String aggregateType;
    private ZonedDateTime timestamp;
    private String eventType;
    private Payload payload;
    private Metadata metadata;

    // Getters and setters

    public static class Payload implements Serializable {
        private String orderId;
        private String customerId;
        private List<Item> items;
        private double totalAmount;
        private String orderStatus;

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }

        public void setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        // Getters and setters

        public static class Item {
            private String itemId;
            private String name;
            private int quantity;
            private double price;

            public void setItemId(String itemId) {
                this.itemId = itemId;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            // Getters and setters
        }
    }

    public static class Metadata {
        private String source;
        private User user;
        private String traceId;
        private List<String> tags;

        public void setSource(String source) {
            this.source = source;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public void setTraceId(String traceId) {
            this.traceId = traceId;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        // Getters and setters

        public static class User {
            private String userId;
            private String username;
            private String ipAddress;

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public void setIpAddress(String ipAddress) {
                this.ipAddress = ipAddress;
            }

            // Getters and setters
        }
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public void setAggregateId(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public String getAggregateType() {
        return aggregateType;
    }

    public void setAggregateType(String aggregateType) {
        this.aggregateType = aggregateType;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
}



