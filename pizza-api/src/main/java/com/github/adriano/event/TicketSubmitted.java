package com.github.adriano.event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.github.adriano.model.Ticket;

public record TicketSubmitted(
    Ticket ticket,
    LocalDateTime submittedAt
) {

    @Override
    public String toString() {
        return String.format("Ticket [%s] submitted at [%s]", ticket.id, submittedAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }
}
