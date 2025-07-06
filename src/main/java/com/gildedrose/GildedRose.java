package com.gildedrose;

import com.gildedrose.strategy.ItemUpdateStrategy;

//This class has been refactored to use the Strategy pattern for handling different item types.
//Each item type's business logic is encapsulated in its own strategy class, making the code more maintainable, testable, and extensible.

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }
    
//Updates the quality and sellIn values for all items in the inventory.
     
    public void updateQuality() {
        for (Item item : items) {
            ItemUpdateStrategy strategy = ItemStrategyFactory.getStrategy(item.name);
            strategy.updateItem(item);
        }
    }
}