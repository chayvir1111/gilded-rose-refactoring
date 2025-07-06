package com.gildedrose;

import com.gildedrose.strategy.*;
import java.util.Objects;

/**
 * Factory class responsible for determining which update strategy to use for each item type.
 * 
 * This factory uses robust matching to handle real-world variations in item names:
 * - Case-insensitive matching
 * - Handles extra whitespace
 * - Supports partial name matching for categories
 * - Graceful handling of null and empty names
 * 
 * @since 2.0
 */
public class ItemStrategyFactory {
    
    // Item type identifiers for robust matching
    private static final String AGED_BRIE = "aged brie";
    private static final String SULFURAS = "sulfuras";
    private static final String BACKSTAGE_PASSES = "backstage passes";
    private static final String CONJURED = "conjured";
    
    /**
     * Returns the appropriate update strategy for the given item name.
     * 
     * Uses robust matching that handles:
     * - Case variations (AGED BRIE, aged brie, Aged Brie)
     * - Extra whitespace (" Aged Brie ")
     * - Partial matches ("Sulfuras, Hand of Ragnaros" matches "sulfuras")
     * - Name variations ("Backstage passes to a TAFKAL80ETC concert - VIP")
     * 
     * @param itemName The name of the item (can be null or empty)
     * @return The strategy to use for updating this item type
     */
    public static ItemUpdateStrategy getStrategy(String itemName) {
        // Handle null and empty names gracefully
        if (itemName == null || itemName.trim().isEmpty()) {
            return new NormalItemStrategy();
        }
        
        // Normalize the name for robust matching
        String normalizedName = itemName.toLowerCase().trim();
        
        // Exact match for Aged Brie
        if (normalizedName.equals(AGED_BRIE)) {
            return new AgedBrieStrategy();
        }
        
        // Contains match for Sulfuras (handles variations like "Sulfuras, Hand of Ragnaros")
        if (normalizedName.contains(SULFURAS)) {
            return new SulfurasStrategy();
        }
        
        // Contains match for Backstage passes (handles variations and suffixes)
        if (normalizedName.contains(BACKSTAGE_PASSES)) {
            return new BackstagePassStrategy();
        }
        
        // Starts with match for Conjured items (handles all conjured variations)
        if (normalizedName.startsWith(CONJURED)) {
            return new ConjuredItemStrategy();
        }
        
        // Default to normal item strategy for unknown types
        return new NormalItemStrategy();
    }
    
    /**
     * Returns information about how the factory would classify the given item name.
     * Useful for debugging and validation.
     * 
     * @param itemName The item name to analyze
     * @return A description of the strategy that would be selected
     */
    public static String getStrategyInfo(String itemName) {
        ItemUpdateStrategy strategy = getStrategy(itemName);
        return String.format("Item '%s' -> %s", itemName, strategy.getDescription());
    }
}