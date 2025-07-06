package com.gildedrose.strategy;

import com.gildedrose.Item;

/**
 * Strategy interface for updating different types of items in the Gilded Rose inventory.
 * Each item type has its own update behavior encapsulated in a concrete strategy.
 */
public interface ItemUpdateStrategy {
    /**
     * Updates the item's quality and sellIn values according to the specific business rules
     * for this item type.
     * 
     * @param item The item to be updated
     */
    void updateItem(Item item);
}			