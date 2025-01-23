package com.example.event_db_connect.controller;
import com.example.event_db_connect.EventRepository;
import com.example.event_db_connect.dto.EventDto;
import com.example.event_db_connect.entity.Event;
import com.example.event_db_connect.entity.Payload;
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
   //private final EventStoreService eventStoreService;
    private final EventRepository eventRepository;


    public EventController(ObjectMapper objectMapper, EventRepository eventRepository) {
        this.eventRepository = eventRepository;
        //   this.eventStoreService = eventStoreService;
      //  this.objectMapper = objectMapper;
       // this.objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    @PostMapping("/api/event")
    public void createEvent(@RequestParam(value = "eventCount") Integer eventCount) throws JsonProcessingException {

        for (int i = 0; i < eventCount; i++) {
            Event event = new Event();
            event.setEventId(UUID.randomUUID().toString());
            event.setAggregateId(UUID.randomUUID().toString());
            event.setAggregateType("Order");
            event.setEventType("OrderCreated");
            event.setTimestamp(ZonedDateTime.now());

            Payload payload = new Payload();
            payload.setOrderId(UUID.randomUUID().toString());
            payload.setCustomerId("customer");
            payload.setItems(List.of(
                    new Payload.Item(UUID.randomUUID().toString(), "sfdsf", 100, 100),
                    new Payload.Item(UUID.randomUUID().toString(), "sfdsf", 100, 100)
            ));
            event.setPayload(payload);

            Long beforTime = System.currentTimeMillis();
            eventRepository.save(event);
            Long afterTime = System.currentTimeMillis();
            Long diff = afterTime - beforTime;


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

    private EventDto getRandomEventObject() {


        Random random = new Random();
      //  int randomIndex = random.nextInt(nameString.length);

        EventDto.Payload.Item item1 = new EventDto.Payload.Item();
        item1.setItemId(UUID.randomUUID().toString());
        item1.setName(nameString[random.nextInt(nameString.length)]);
        item1.setQuantity(1);
        item1.setPrice(29.99);

        EventDto.Payload.Item item2 = new EventDto.Payload.Item();
        item2.setItemId(UUID.randomUUID().toString());
        item2.setName(nameString[random.nextInt(nameString.length)]);
        item2.setQuantity(1);
        item2.setPrice(89.99);

        // Create payload
        EventDto.Payload payload = new EventDto.Payload();
        payload.setOrderId(UUID.randomUUID().toString());
        payload.setCustomerId(UUID.randomUUID().toString());
        payload.setItems(Arrays.asList(item1, item2));
        payload.setTotalAmount(119.98);
        payload.setOrderStatus(nameString[random.nextInt(nameString.length)]);

        // Create user
        EventDto.Metadata.User user = new EventDto.Metadata.User();
        user.setUserId(UUID.randomUUID().toString());
        user.setUsername(nameString[random.nextInt(nameString.length)]);
        user.setIpAddress(nameString[random.nextInt(nameString.length)]);

        // Create metadata
        EventDto.Metadata metadata = new EventDto.Metadata();
        metadata.setSource(nameString[random.nextInt(nameString.length)]);
        metadata.setUser(user);
        metadata.setTraceId(UUID.randomUUID().toString());
        metadata.setTags(Arrays.asList(nameString[random.nextInt(nameString.length)],nameString[random.nextInt(nameString.length)]));

        // Create the event
        EventDto eventDto = new EventDto();
        eventDto.setEventId(UUID.randomUUID().toString());
        eventDto.setAggregateId(UUID.randomUUID().toString());
        eventDto.setAggregateType(nameString[random.nextInt(nameString.length)]);
        eventDto.setTimestamp(ZonedDateTime.parse("2025-01-23T15:30:45.123Z"));
        eventDto.setEventType(nameString[random.nextInt(nameString.length)]);
        eventDto.setPayload(payload);
        eventDto.setMetadata(metadata);


        return eventDto;
    }
}


