package com.example.event_db_connect;

import com.example.event_db_connect.dto.Event;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;

@Service
public class EventService {

    private String[] nameString = {"Wireless Mourse", "Mechanical Keyboard", "placed", "Johndoe", "e-commerce-service", "Order", "OrderPlaced"};

    static List<List<String>> dataList = new ArrayList<>();

    @Autowired
    private MongoDBConfig mongoDBConfig;

    public void saveEntity(Integer eventCount) {

        for(int i = 0; i < eventCount; i++) {
        MongoCollection<Document> collection = mongoDBConfig.getCollection("event_source");
        Document entity = new Document();
        Random random = new Random();
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


        Event.Payload payload = new Event.Payload();
        payload.setOrderId(UUID.randomUUID().toString());
        payload.setCustomerId(UUID.randomUUID().toString());
        payload.setItems(Arrays.asList(item1, item2));
        payload.setTotalAmount(119.98);
        payload.setOrderStatus(nameString[random.nextInt(nameString.length)]);


        Event.Metadata.User user = new Event.Metadata.User();
        user.setUserId(UUID.randomUUID().toString());
        user.setUsername(nameString[random.nextInt(nameString.length)]);
        user.setIpAddress(nameString[random.nextInt(nameString.length)]);

        Event.Metadata metadata = new Event.Metadata();
        metadata.setSource("source");
        metadata.setUser(user);
        metadata.setTraceId(UUID.randomUUID().toString());
        metadata.setTags(Arrays.asList(nameString[random.nextInt(nameString.length)],nameString[random.nextInt(nameString.length)]));

        Event event = new Event();
        event.setEventId(UUID.randomUUID().toString());
        event.setAggregateId(UUID.randomUUID().toString());
        event.setAggregateType(nameString[random.nextInt(nameString.length)]);
        event.setTimestamp(ZonedDateTime.parse("2025-01-23T15:30:45.123Z"));
        event.setEventType(nameString[random.nextInt(nameString.length)]);
        event.setPayload(payload);
        event.setMetadata(metadata);

        //entity.append("event", event);

            Long beforeTime = System.currentTimeMillis();
            entity.append("event".concat(String.valueOf(i)), event);
            collection.insertOne(entity);
            Long afterTime = System.currentTimeMillis();
            long diff = afterTime - beforeTime;

            List<String> row = new ArrayList<>();
            row.add(event.getEventId());
            row.add(String.valueOf(diff));
            dataList.add(row);
        }

        writeToCSV(eventCount);
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



}
