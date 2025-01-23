package com.example.event_db_connect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventDbConnectApplication implements CommandLineRunner {

	@Autowired
	private EventStoreService eventStoreService;

	public static void main(String[] args) {
		SpringApplication.run(EventDbConnectApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
//		String streamName = "example-stream";
//		String eventType = "UserCreated";
//		String eventData = "{\"userId\":\"123\", \"name\":\"John Doe\"}";
//
//		// Append an event
//        try {
//            eventStoreService.appendEvent(streamName, eventType, eventData);
//        } catch (Throwable e) {
//            throw new RuntimeException(e);
//        }

        // Read events
//        try {
//            eventStoreService.readEvents(streamName);
//        } catch (Throwable e) {
//            throw new RuntimeException(e);
//        }
    }
}
