package com.gildedrose.strategy;

import com.gildedrose.Item;
import java.util.Objects;

/**
 * Strategy for Conjured items which degrade twice as fast as normal items.
 * 
 * Business rules:
 * - Quality decreases by 2 each day before sell date
 * - Quality decreases by 4 each day after sell date has passed (2x degradation rate)
 * - Quality never goes below 0
 * - SellIn decreases by 1 each day
 * 
 * @since 2.0
 */
public class ConjuredItemStrategy implements ItemUpdateStrategy {
    
    private static final int CONJURED_DEGRADATION_RATE = 2;
    
    @Override
    public void updateItem(Item item) {
        Objects.requireNonNull(item, "Item cannot be null");
        
        decreaseQuality(item, CONJURED_DEGRADATION_RATE);
        decreaseSellIn(item);
        
        if (hasSellDatePassed(item)) {
            decreaseQuality(item, CONJURED_DEGRADATION_RATE); // Double degradation after sell date
        }
    }
    
    /**
     * Decreases item quality by the specified amount, ensuring it never goes below 0.
     * 
     * @param item The item to update
     * @param amount The amount to decrease (must be positive)
     */
    private void decreaseQuality(Item item, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Degradation amount must be positive");
        }
        
        // Decrease quality by amount, but never below 0
        item.quality = Math.max(0, item.quality - amount);
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
        return "Conjured Item Strategy (quality decreases by 2/day, 4/day after sell date)";
    }
}