package com.gildedrose.strategy;

import com.gildedrose.Item;

/**
 * Strategy for Aged Brie which increases in quality as it ages.
 * Business rules:
 * - Quality increases by 1 each day
 * - Quality increases by 2 each day after sell date has passed
 * - Quality never exceeds 50
 */
public class AgedBrieStrategy implements ItemUpdateStrategy {
    
    private static final int MAX_QUALITY = 50;
    
    @Override
    public void updateItem(Item item) {
        increaseQuality(item);
        decreaseSellIn(item);
        
        if (hasSellDatePassed(item)) {
            increaseQuality(item); // Double improvement after sell date
        }
    }
    
    private void increaseQuality(Item item) {
        if (item.quality < MAX_QUALITY) {
            item.quality++;
        }
    }
    
    private void decreaseSellIn(Item item) {
        item.sellIn--;
    }
    
    private boolean hasSellDatePassed(Item item) {
        return item.sellIn < 0;
    }
}