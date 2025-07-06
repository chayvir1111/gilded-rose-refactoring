package com.gildedrose.strategy;

import com.gildedrose.Item;

/**
 * Strategy for Sulfuras, a legendary item that never changes.
 * Business rules:
 * - Quality never changes (always 80)
 * - SellIn never changes
 * - Being legendary, it doesn't follow normal rules
 */
public class SulfurasStrategy implements ItemUpdateStrategy {
    
    @Override
    public void updateItem(Item item) {
        // Sulfuras never changes - it's a legendary item
        // No quality or sellIn modifications needed
    }
}