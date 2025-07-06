package com.gildedrose.strategy;

import com.gildedrose.Item;
import java.util.Objects;

/**
 * Strategy for normal items that degrade in quality over time.
 * 
 * Business rules:
 * - Quality decreases by 1 each day before sell date
 * - Quality decreases by 2 each day after sell date has passed
 * - Quality never goes below 0
 * - SellIn decreases by 1 each day
 * 
 * @since 2.0
 */
public class NormalItemStrategy implements ItemUpdateStrategy {
    
    @Override
    public void updateItem(Item item) {
        Objects.requireNonNull(item, "Item cannot be null");
        
        decreaseQuality(item);
        decreaseSellIn(item);
        
        if (hasSellDatePassed(item)) {
            decreaseQuality(item); // Double degradation after sell date
        }
    }
    
    /**
     * Decreases item quality by 1, ensuring it never goes below 0.
     */
    private void decreaseQuality(Item item) {
        if (item.quality > 0) {
            item.quality--;
        }
    }
    
    /**
     * Decreases sellIn by 1 day.
     */
    private void decreaseSellIn(Item item) {
        item.sellIn--;
    }
    
    /**
     * Checks if the sell date has passed.
     */
    private boolean hasSellDatePassed(Item item) {
        return item.sellIn < 0;
    }
    
    @Override
    public String getDescription() {
        return "Normal Item Strategy (quality decreases by 1/day, 2/day after sell date)";
    }
}