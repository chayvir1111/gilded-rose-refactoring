package com.gildedrose.strategy;

import com.gildedrose.Item;

/**
 * Strategy for Backstage passes which have complex quality rules based on days until concert.
 * Business rules:
 * - Quality increases by 1 when more than 10 days left
 * - Quality increases by 2 when 6-10 days left
 * - Quality increases by 3 when 1-5 days left
 * - Quality drops to 0 after the concert (sellIn < 0)
 * - Quality never exceeds 50
 */
public class BackstagePassStrategy implements ItemUpdateStrategy {
    
    private static final int MAX_QUALITY = 50;
    private static final int DOUBLE_INCREASE_THRESHOLD = 10;
    private static final int TRIPLE_INCREASE_THRESHOLD = 5;
    
    @Override
    public void updateItem(Item item) {
        increaseQualityBasedOnDaysLeft(item);
        decreaseSellIn(item);
        
        if (hasConcertPassed(item)) {
            item.quality = 0; // Worthless after concert
        }
    }
    
    private void increaseQualityBasedOnDaysLeft(Item item) {
        if (item.quality >= MAX_QUALITY) {
            return;
        }
        
        // Always increase by 1
        item.quality++;
        
        // Additional increases based on days left
        if (item.sellIn <= DOUBLE_INCREASE_THRESHOLD && item.quality < MAX_QUALITY) {
            item.quality++; // +1 more when 10 days or less
        }
        
        if (item.sellIn <= TRIPLE_INCREASE_THRESHOLD && item.quality < MAX_QUALITY) {
            item.quality++; // +1 more when 5 days or less
        }
    }
    
    private void decreaseSellIn(Item item) {
        item.sellIn--;
    }
    
    private boolean hasConcertPassed(Item item) {
        return item.sellIn < 0;
    }
}