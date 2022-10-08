package com.cartchain.application.request;

import com.cartchain.domain.model.Item;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class CreateCartReq {
    @NotNull
    private List<Item> items;
}
