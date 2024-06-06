package org.acme.ShoppingList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddIngredientsDTO {
    @JsonProperty("customUserId")
    public Long customUserId;

    @JsonProperty("ingredientId")
    public Long ingredientId;

    @JsonProperty("amount")
    public Float amount;
}
