package com.github.adriano.resource.records;

import java.math.BigDecimal;

public record TicketItemAdd(
    Long pizzaId,
    BigDecimal price,
    Integer quantity) {
    
}
