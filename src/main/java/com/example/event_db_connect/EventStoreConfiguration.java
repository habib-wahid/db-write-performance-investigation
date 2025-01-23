package com.example.event_db_connect;

import com.eventstore.dbclient.EventStoreDBClient;
import com.eventstore.dbclient.EventStoreDBClientSettings;
import com.eventstore.dbclient.EventStoreDBConnectionString;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventStoreConfiguration {

    @Bean
    public EventStoreDBClient eventStoreDBClient() {
        EventStoreDBClientSettings settings = EventStoreDBConnectionString.parseOrThrow(
                "esdb://localhost:2113?tls=false"
        );
        return EventStoreDBClient.create(settings);
    }
}
