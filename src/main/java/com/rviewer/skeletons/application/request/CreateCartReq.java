package com.rviewer.skeletons.application.request;

import com.rviewer.skeletons.domain.model.Item;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class CreateCartReq {
    @NotNull
    private List<Item> items;
}
