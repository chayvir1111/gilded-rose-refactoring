package com.gildedrose.strategy;

import com.gildedrose.Item;
import java.util.Objects;

/**
 * Strategy for Backstage passes which have complex quality rules based on days until concert.
 * 
 * Business rules:
 * - Quality increases by 1 when more than 10 days left
 * - Quality increases by 2 when 6-10 days left (inclusive)
 * - Quality increases by 3 when 1-5 days left (inclusive)
 * - Quality drops to 0 after the concert (sellIn < 0)
 * - Quality never exceeds 50
 * - SellIn decreases by 1 each day
 * 
 * @since 2.0
 */
public class BackstagePassStrategy implements ItemUpdateStrategy {
    
    private static final int MAX_QUALITY = 50;
    private static final int DOUBLE_INCREASE_THRESHOLD = 10;
    private static final int TRIPLE_INCREASE_THRESHOLD = 5;
    
    @Override
    public void updateItem(Item item) {
        Objects.requireNonNull(item, "Item cannot be null");
        
        increaseQualityBasedOnDaysLeft(item);
        decreaseSellIn(item);
        
        if (hasConcertPassed(item)) {
            item.quality = 0; // Worthless after concert
        }
    }
    
    /**
     * Increases quality based on days remaining until concert.
     * Uses tiered approach: more days = less increase, fewer days = more increase.
     */
    private void increaseQualityBasedOnDaysLeft(Item item) {
        if (item.quality >= MAX_QUALITY) {
            return; // Already at maximum quality
        }
        
        // Always increase by 1 (base increase)
        item.quality = Math.min(item.quality + 1, MAX_QUALITY);
        
        // Additional increase when 10 days or fewer
        if (item.sellIn <= DOUBLE_INCREASE_THRESHOLD && item.quality < MAX_QUALITY) {
            item.quality = Math.min(item.quality + 1, MAX_QUALITY);
        }
        
        // Additional increase when 5 days or fewer
        if (item.sellIn <= TRIPLE_INCREASE_THRESHOLD && item.quality < MAX_QUALITY) {
            item.quality = Math.min(item.quality + 1, MAX_QUALITY);
        }
    }
    
    /**
     * Decreases sellIn by 1 day.
     */
    private void decreaseSellIn(Item item) {
        item.sellIn--;
    }
    
    /**
     * Checks if the concert has passed.
     */
    private boolean hasConcertPassed(Item item) {
        return item.sellIn < 0;
    }
    
    @Override
    public String getDescription() {
        return "Backstage Pass Strategy (quality increases 1/2/3 based on days left, 0 after concert)";
    }
}