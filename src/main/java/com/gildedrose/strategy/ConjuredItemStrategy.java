package com.gildedrose.strategy;

import com.gildedrose.Item;

/**
 * Strategy for Conjured items which degrade twice as fast as normal items.
 * Business rules:
 * - Quality decreases by 2 each day
 * - Quality decreases by 4 each day after sell date has passed
 * - Quality never goes below 0
 */
public class ConjuredItemStrategy implements ItemUpdateStrategy {
    
    private static final int CONJURED_DEGRADATION_RATE = 2;
    
    @Override
    public void updateItem(Item item) {
        decreaseQuality(item, CONJURED_DEGRADATION_RATE);
        decreaseSellIn(item);
        
        if (hasSellDatePassed(item)) {
            decreaseQuality(item, CONJURED_DEGRADATION_RATE); // Double degradation after sell date
        }
    }
    
    private void decreaseQuality(Item item, int amount) {
        for (int i = 0; i < amount && item.quality > 0; i++) {
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