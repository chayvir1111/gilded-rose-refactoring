package com.gildedrose.strategy;

public class AgedBrieStrategy {

}
package com.gildedrose.strategy;

import com.gildedrose.Item;
import java.util.Objects;

/**
 * Strategy for Aged Brie which increases in quality as it ages.
 * 
 * Business rules:
 * - Quality increases by 1 each day before sell date
 * - Quality increases by 2 each day after sell date has passed
 * - Quality never exceeds 50
 * - SellIn decreases by 1 each day
 * 
 * @since 2.0
 */
public class AgedBrieStrategy implements ItemUpdateStrategy {
    
    private static final int MAX_QUALITY = 50;
    
    @Override
    public void updateItem(Item item) {
        Objects.requireNonNull(item, "Item cannot be null");
        
        increaseQuality(item);
        decreaseSellIn(item);
        
        if (hasSellDatePassed(item)) {
            increaseQuality(item); // Double improvement after sell date
        }
    }
    
    /**
     * Increases item quality by 1, ensuring it never exceeds maximum.
     */
    private void increaseQuality(Item item) {
        if (item.quality < MAX_QUALITY) {
            item.quality++;
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
        return "Aged Brie Strategy (quality increases by 1/day, 2/day after sell date, max 50)";
    }
}