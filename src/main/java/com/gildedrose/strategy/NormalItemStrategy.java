package com.gildedrose.strategy;

import com.gildedrose.Item;

/**
 * Strategy for normal items that degrade in quality over time.
 * Business rules:
 * - Quality decreases by 1 each day
 * - Quality decreases by 2 each day after sell date has passed
 * - Quality never goes below 0
 */
public class NormalItemStrategy implements ItemUpdateStrategy {
    
    @Override
    public void updateItem(Item item) {
        decreaseQuality(item);
        decreaseSellIn(item);
        
        if (hasSellDatePassed(item)) {
            decreaseQuality(item); // Double degradation after sell date
        }
    }
    
    private void decreaseQuality(Item item) {
        if (item.quality > 0) {
            item.quality--;
        }
    }
    
    private void decreaseSellIn(Item item) {
        item.sellIn--;
    }
    
    private boolean hasSellDatePassed(Item item) {
        return item.sellIn < 0;
    }
}