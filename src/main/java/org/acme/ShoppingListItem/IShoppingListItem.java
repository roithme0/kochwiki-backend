package org.acme.ShoppingListItem;

import org.acme.FoodstuffMetaData.UnitEnum;

public interface IShoppingListItem {
    Boolean getIsChecked();

    String getName();

    String getBrand();

    Float getAmount();

    UnitEnum getUnit();

    String getUnitVerbose();
}
