package com.gildedrose.strategy;

import com.gildedrose.Item;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ConjuredItemStrategyTest {

    private final ConjuredItemStrategy strategy = new ConjuredItemStrategy();

    @Test
    @DisplayName("Conjured item should degrade by 2 before sell date")
    void conjuredItemDegradesByTwoBeforeSellDate() {
        Item item = new Item("Conjured Item", 5, 10);
        
        strategy.updateItem(item);
        
        assertEquals(4, item.sellIn);
        assertEquals(8, item.quality);
    }

    @Test
    @DisplayName("Conjured item should degrade by 4 after sell date")
    void conjuredItemDegradesByFourAfterSellDate() {
        Item item = new Item("Conjured Item", 0, 10);
        
        strategy.updateItem(item);
        
        assertEquals(-1, item.sellIn);
        assertEquals(6, item.quality);
    }

    @Test
    @DisplayName("Conjured item quality should never go below 0")
    void conjuredItemQualityNeverGoesNegative() {
        Item item = new Item("Conjured Item", 5, 1);
        
        strategy.updateItem(item);
        
        assertEquals(0, item.quality);
    }

    @Test
    @DisplayName("Conjured item with 0 quality stays at 0")
    void conjuredItemWithZeroQualityStaysZero() {
        Item item = new Item("Conjured Item", 5, 0);
        
        strategy.updateItem(item);
        
        assertEquals(0, item.quality);
        assertEquals(4, item.sellIn); // sellIn still decreases
    }
}