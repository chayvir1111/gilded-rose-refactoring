package com.gildedrose;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class GildedRoseTest {

    @Test
    @DisplayName("Normal items should degrade in quality by 1 each day")
    void normalItemsDegradeByOnePerDay() {
        Item[] items = new Item[] { new Item("Normal Item", 10, 20) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(9, items[0].sellIn);
        assertEquals(19, items[0].quality);
    }

    @Test
    @DisplayName("Normal items should degrade twice as fast after sell date")
    void normalItemsDegradeTwiceAsFastAfterSellDate() {
        Item[] items = new Item[] { new Item("Normal Item", 0, 20) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(-1, items[0].sellIn);
        assertEquals(18, items[0].quality); // Decreased by 2
    }

    @Test
    @DisplayName("Quality should never go below 0")
    void qualityNeverGoesNegative() {
        Item[] items = new Item[] { new Item("Normal Item", 5, 0) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(0, items[0].quality);
    }

    @Test
    @DisplayName("Aged Brie should increase in quality over time")
    void agedBrieIncreasesInQuality() {
        Item[] items = new Item[] { new Item("Aged Brie", 10, 20) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(9, items[0].sellIn);
        assertEquals(21, items[0].quality);
    }

    @Test
    @DisplayName("Aged Brie should increase twice as fast after sell date")
    void agedBrieIncreasesTwiceAsFastAfterSellDate() {
        Item[] items = new Item[] { new Item("Aged Brie", 0, 20) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(-1, items[0].sellIn);
        assertEquals(22, items[0].quality); // Increased by 2
    }

    @Test
    @DisplayName("Quality should never exceed 50")
    void qualityNeverExceedsFifty() {
        Item[] items = new Item[] { new Item("Aged Brie", 10, 50) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(50, items[0].quality);
    }

    @Test
    @DisplayName("Sulfuras should never change")
    void sulfurasNeverChanges() {
        Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 10, 80) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(10, items[0].sellIn);
        assertEquals(80, items[0].quality);
    }

    @Test
    @DisplayName("Backstage passes should increase in quality as concert approaches")
    void backstagePassesIncreaseAsEventApproaches() {
        // More than 10 days: +1 quality
        Item[] items1 = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20) };
        GildedRose app1 = new GildedRose(items1);
        app1.updateQuality();
        assertEquals(21, items1[0].quality);
        
        // 6-10 days: +2 quality
        Item[] items2 = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20) };
        GildedRose app2 = new GildedRose(items2);
        app2.updateQuality();
        assertEquals(22, items2[0].quality);
        
        // 1-5 days: +3 quality
        Item[] items3 = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20) };
        GildedRose app3 = new GildedRose(items3);
        app3.updateQuality();
        assertEquals(23, items3[0].quality);
    }

    @Test
    @DisplayName("Backstage passes should become worthless after concert")
    void backstagePassesBecomeWorthlessAfterConcert() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(-1, items[0].sellIn);
        assertEquals(0, items[0].quality);
    }

    @Test
    @DisplayName("Multiple days simulation should work correctly")
    void multipleDaysSimulation() {
        Item[] items = new Item[] {
            new Item("Normal Item", 5, 10),
            new Item("Aged Brie", 3, 5),
            new Item("Sulfuras, Hand of Ragnaros", 0, 80)
        };
        GildedRose app = new GildedRose(items);
        
        // Simulate 3 days
        for (int day = 0; day < 3; day++) {
            app.updateQuality();
        }
        
        // Normal item: 5-3=2 sellIn, 10-3=7 quality
        assertEquals(2, items[0].sellIn);
        assertEquals(7, items[0].quality);
        
        // Aged Brie: 3-3=0 sellIn, 5+3=8 quality
        assertEquals(0, items[1].sellIn);
        assertEquals(8, items[1].quality);
        
        // Sulfuras: never changes
        assertEquals(0, items[2].sellIn);
        assertEquals(80, items[2].quality);
    }

    @Test
    @DisplayName("Comprehensive original behavior preservation test")
    void originalBehaviorPreservation() {
        // Test with the exact items from the original TexttestFixture
        Item[] items = new Item[] {
            new Item("+5 Dexterity Vest", 10, 20),
            new Item("Aged Brie", 2, 0),
            new Item("Elixir of the Mongoose", 5, 7),
            new Item("Sulfuras, Hand of Ragnaros", 0, 80),
            new Item("Sulfuras, Hand of Ragnaros", -1, 80),
            new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
            new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
            new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49)
        };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        // Verify expected behavior for each item
        assertEquals(9, items[0].sellIn);
        assertEquals(19, items[0].quality);
        
        assertEquals(1, items[1].sellIn);
        assertEquals(1, items[1].quality);
        
        assertEquals(4, items[2].sellIn);
        assertEquals(6, items[2].quality);
        
        assertEquals(0, items[3].sellIn);
        assertEquals(80, items[3].quality);
        
        assertEquals(-1, items[4].sellIn);
        assertEquals(80, items[4].quality);
        
        assertEquals(14, items[5].sellIn);
        assertEquals(21, items[5].quality);
        
        assertEquals(9, items[6].sellIn);
        assertEquals(50, items[6].quality);
        
        assertEquals(4, items[7].sellIn);
        assertEquals(50, items[7].quality);
    }
    @Test
    @DisplayName("Conjured items should degrade twice as fast as normal items")
    void conjuredItemsDegradeTwiceAsFast() {
        Item[] items = new Item[] { new Item("Conjured Mana Cake", 10, 20) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(9, items[0].sellIn);
        assertEquals(18, items[0].quality); // Decreased by 2
    }

    @Test
    @DisplayName("Conjured items should degrade 4x as fast after sell date")
    void conjuredItemsDegradeFourTimesAsFastAfterSellDate() {
        Item[] items = new Item[] { new Item("Conjured Mana Cake", 0, 20) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(-1, items[0].sellIn);
        assertEquals(16, items[0].quality); // Decreased by 4 (2 + 2)
    }

    @Test
    @DisplayName("Conjured items quality should never go below 0")
    void conjuredItemQualityNeverGoesNegative() {
        Item[] items = new Item[] { new Item("Conjured Mana Cake", 5, 1) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(0, items[0].quality);
    }

    @Test
    @DisplayName("Various conjured item names should work")
    void variousConjuredItemNamesWork() {
        Item[] items = new Item[] {
            new Item("Conjured Mana Cake", 5, 10),
            new Item("Conjured Sword", 3, 8),
            new Item("Conjured Shield of Power", 1, 6)
        };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        // All should decrease by 2
        assertEquals(8, items[0].quality);
        assertEquals(6, items[1].quality);
        assertEquals(4, items[2].quality);
    }

    @Test
    @DisplayName("Mixed item types work together correctly")
    void mixedItemTypesWorkTogether() {
        Item[] items = new Item[] {
            new Item("Normal Item", 5, 10),
            new Item("Aged Brie", 5, 5),
            new Item("Conjured Mana Cake", 5, 10),
            new Item("Sulfuras, Hand of Ragnaros", 5, 80)
        };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        
        assertEquals(9, items[0].quality);  // Normal: -1
        assertEquals(6, items[1].quality);  // Aged Brie: +1
        assertEquals(8, items[2].quality);  // Conjured: -2
        assertEquals(80, items[3].quality); // Sulfuras: no change
    }
}