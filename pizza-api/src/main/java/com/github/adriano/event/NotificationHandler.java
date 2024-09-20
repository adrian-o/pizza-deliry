package com.github.adriano.event;

import io.quarkus.logging.Log;
import jakarta.enterprise.event.Observes;

public class NotificationHandler {
    
    public void onTicketSubmitted(@Observes TicketSubmitted evt) {
        Log.infof("--> NOTIFICATION Event: %s", evt.toString());
    }
}
