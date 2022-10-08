package com.cartchain.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class Item {
    @NotNull
    private UUID id;
    @NotNull
    private String name;
    @NotNull
    @NumberFormat
    private Integer quantity;
    @NotNull
    @NumberFormat
    private Float price;
}
