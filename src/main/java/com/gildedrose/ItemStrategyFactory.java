package com.gildedrose;

import com.gildedrose.strategy.*;

/**
 * Factory class responsible for determining which update strategy to use for each item type.
 * This centralizes the logic for mapping item names to their corresponding behaviors.
 */
public class ItemStrategyFactory {
    
    // Item type constants for clarity and maintainability
    public static final String AGED_BRIE = "Aged Brie";
    public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    public static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    public static final String CONJURED_PREFIX = "Conjured";
    
    /**
     * Returns the appropriate update strategy for the given item name.
     * 
     * @param itemName The name of the item
     * @return The strategy to use for updating this item type
     */
    public static ItemUpdateStrategy getStrategy(String itemName) {
        if (itemName.equals(AGED_BRIE)) {
            return new AgedBrieStrategy();
        }
        
        if (itemName.equals(SULFURAS)) {
            return new SulfurasStrategy();
        }
        
        if (itemName.equals(BACKSTAGE_PASSES)) {
            return new BackstagePassStrategy();
        }
        
        if (itemName.startsWith(CONJURED_PREFIX)) {
            return new ConjuredItemStrategy();
        }
        
        // Default to normal item strategy
        return new NormalItemStrategy();
    }
}