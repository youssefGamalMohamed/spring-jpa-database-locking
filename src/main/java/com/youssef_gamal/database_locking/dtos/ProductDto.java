package com.youssef_gamal.database_locking.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record ProductDto(
    String id,

    @NotBlank(message = "Name is mandatory")
    String name,

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be positive")
    double price
) {
}
