package com.example.event_db_connect;

import com.eventstore.dbclient.EventData;
import com.eventstore.dbclient.EventStoreDBClient;
import com.eventstore.dbclient.ReadStreamOptions;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EventStoreService {
    private final EventStoreDBClient eventStoreDBClient;

    static List<List<String>> dataList = new ArrayList<>();

    public EventStoreService(EventStoreDBClient eventStoreDBClient) {
        this.eventStoreDBClient = eventStoreDBClient;
    }

    public void appendEvent(String streamName, String eventType, String data, Integer i, Integer eventCount) throws Throwable {
        EventData event = EventData.builderAsJson(eventType, data)
                .build();

        Long beforeTime = System.currentTimeMillis();
        eventStoreDBClient.appendToStream(streamName, event).get();
        Long afterTime = System.currentTimeMillis();
        Long timeDiff = afterTime - beforeTime;
        List<String> row = new ArrayList<>();
        row.add(UUID.randomUUID().toString());
        row.add(timeDiff.toString());
        dataList.add(row);

        if (i == eventCount - 1) {
            writeToCSV(i);
        }
    }

    public void readEvents(String streamName) throws Throwable {
        ReadStreamOptions options = ReadStreamOptions.get()
                .forwards()
                .fromStart();

        eventStoreDBClient.readStream(streamName, options)
                .get()
                .getEvents()
                .forEach(resolvedEvent -> {
                    System.out.println(new String(resolvedEvent.getEvent().getEventData()));
                });
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
