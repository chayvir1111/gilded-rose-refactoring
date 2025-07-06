package com.gildedrose.strategy;

import com.gildedrose.Item;
import java.util.Objects;

/**
 * Strategy for Sulfuras, a legendary item that never changes.
 * 
 * Business rules:
 * - Quality never changes (always remains at its current value, typically 80)
 * - SellIn never changes
 * - Being legendary, it doesn't follow normal degradation rules
 * 
 * @since 2.0
 */
public class SulfurasStrategy implements ItemUpdateStrategy {
    
    @Override
    public void updateItem(Item item) {
        Objects.requireNonNull(item, "Item cannot be null");
        
        // Sulfuras never changes - it's a legendary item
        // No quality or sellIn modifications needed
        // This is intentionally empty and correct
    }
    
    @Override
    public String getDescription() {
        return "Sulfuras Strategy (legendary item - never changes)";
    }
}