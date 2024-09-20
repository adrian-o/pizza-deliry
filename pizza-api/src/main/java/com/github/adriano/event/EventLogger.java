package com.github.adriano.event;

import io.quarkus.logging.Log;
import jakarta.enterprise.event.Observes;

public class EventLogger {
    
    public void onTicketSubmitted(@Observes TicketSubmitted evt) {
        Log.infof("--> Application LOG: %s", evt.toString());
    }
}
