package com.example.event_db_connect.controller;
import com.example.event_db_connect.dto.Event;
import com.example.event_db_connect.EventStoreService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;


@RestController
public class EventController {

    static List<List<String>> dataList = new ArrayList<>();
    private String[] nameString = {"Wireless Mourse", "Mechanical Keyboard", "placed", "Johndoe", "e-commerce-service", "Order", "OrderPlaced"};
    private final EventStoreService eventStoreService;
    private final ObjectMapper objectMapper;

    public EventController(EventStoreService eventStoreService, ObjectMapper objectMapper) {
        this.eventStoreService = eventStoreService;
        this.objectMapper = objectMapper;
        this.objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    @PostMapping("/api/event")
    public void createEvent(@RequestParam(value = "eventCount") Integer eventCount) throws JsonProcessingException {
        for (int i = 0; i < eventCount; i++) {
            Event event = getRandomEventObject();
            String streamName = "Stream_".concat(String.valueOf(i));
            String eventJson = objectMapper.writeValueAsString(event);
            // Append an event
            try {
                Long beforeTime = System.currentTimeMillis();
                eventStoreService.appendEvent(streamName, "APPEND", eventJson, i, eventCount);
                Long afterTime = System.currentTimeMillis();
                Long timeDiff = afterTime - beforeTime;

                List<String> row = new ArrayList<>();
                row.add(event.getEventId());
                row.add(timeDiff.toString());
                dataList.add(row);

            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void writeToCSV(int times) {
        String FILE_PATH = "/home/habibur/Documents/"+times+"_events_log_at"+ LocalDateTime.now()+".csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            if (Files.size(Paths.get(FILE_PATH)) == 0) {
                writer.append("eventidentifier,store time\n");
            }
            for (List<String> row : dataList) {
                writer.append(row.get(0)).append(",").append(row.get(1)).append("\n");
            }
          //  counter = 0;
            System.out.println("Finish writing to file");
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    private Event getRandomEventObject() {


        Random random = new Random();
      //  int randomIndex = random.nextInt(nameString.length);

        Event.Payload.Item item1 = new Event.Payload.Item();
        item1.setItemId(UUID.randomUUID().toString());
        item1.setName(nameString[random.nextInt(nameString.length)]);
        item1.setQuantity(1);
        item1.setPrice(29.99);

        Event.Payload.Item item2 = new Event.Payload.Item();
        item2.setItemId(UUID.randomUUID().toString());
        item2.setName(nameString[random.nextInt(nameString.length)]);
        item2.setQuantity(1);
        item2.setPrice(89.99);

        // Create payload
        Event.Payload payload = new Event.Payload();
        payload.setOrderId(UUID.randomUUID().toString());
        payload.setCustomerId(UUID.randomUUID().toString());
        payload.setItems(Arrays.asList(item1, item2));
        payload.setTotalAmount(119.98);
        payload.setOrderStatus(nameString[random.nextInt(nameString.length)]);

        // Create user
        Event.Metadata.User user = new Event.Metadata.User();
        user.setUserId(UUID.randomUUID().toString());
        user.setUsername(nameString[random.nextInt(nameString.length)]);
        user.setIpAddress(nameString[random.nextInt(nameString.length)]);

        // Create metadata
        Event.Metadata metadata = new Event.Metadata();
        metadata.setSource(nameString[random.nextInt(nameString.length)]);
        metadata.setUser(user);
        metadata.setTraceId(UUID.randomUUID().toString());
        metadata.setTags(Arrays.asList(nameString[random.nextInt(nameString.length)],nameString[random.nextInt(nameString.length)]));

        // Create the event
        Event event = new Event();
        event.setEventId(UUID.randomUUID().toString());
        event.setAggregateId(UUID.randomUUID().toString());
        event.setAggregateType(nameString[random.nextInt(nameString.length)]);
        event.setTimestamp(ZonedDateTime.parse("2025-01-23T15:30:45.123Z"));
        event.setEventType(nameString[random.nextInt(nameString.length)]);
        event.setPayload(payload);
        event.setMetadata(metadata);


        return event;
    }
}


