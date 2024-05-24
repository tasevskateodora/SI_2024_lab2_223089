package org.example;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;



public class SILab2Test {

    // Helper method to create an Item
    private Item createItem(String name, String barcode, int price, float discount) {
        return new Item(name, barcode, price, discount);
    }

    @Test
    public void testEveryBranch() {
        // Test case 1: allItems is null
        try {
            SILab2.checkCart(null, 100);
            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("allItems list can't be null!", e.getMessage());
        }

        // Test case 2: empty allItems list
        assertTrue(SILab2.checkCart(Collections.emptyList(), 100));

        // Test case 3: item with null name and valid barcode, no discount
        Item item3 = createItem(null, "123456", 50, 0);
        assertTrue(SILab2.checkCart(Collections.singletonList(item3), 100));
        assertEquals("unknown", item3.getName());

        // Test case 4: item with valid name and no barcode
        Item item4 = createItem("item4", null, 50, 0);
        try {
            SILab2.checkCart(Collections.singletonList(item4), 100);
            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("No barcode!", e.getMessage());
        }

        // Test case 5: item with valid name and invalid barcode character
        Item item5 = createItem("item5", "123a56", 50, 0);
        try {
            SILab2.checkCart(Collections.singletonList(item5), 100);
            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("Invalid character in item barcode!", e.getMessage());
        }

        // Test case 6: item with valid barcode and discount
        Item item6 = createItem("item6", "123456", 50, 0.1f);
        assertTrue(SILab2.checkCart(Collections.singletonList(item6), 100));

        // Test case 7: item with price > 300, discount > 0, and barcode starts with '0'
        Item item7 = createItem("item7", "0123456", 350, 0.1f);
        assertTrue(SILab2.checkCart(Collections.singletonList(item7), 31));

        // Test case 8: total sum greater than payment
        Item item8 = createItem("item8", "123456", 150, 0);
        assertFalse(SILab2.checkCart(Collections.singletonList(item8), 100));
    }

    @Test
    public void testMultipleCondition() {
        // Test case 1: C1 = true, C2 = true, C3 = true
        Item item1 = createItem("item1", "0123456", 350, 0.1f);
        assertTrue(SILab2.checkCart(Collections.singletonList(item1), 100));

        // Test case 2: C1 = true, C2 = true, C3 = false
        Item item2 = createItem("item2", "1123456", 350, 0.1f);
        assertFalse(SILab2.checkCart(Collections.singletonList(item2), 30));

        // Test case 3: C1 = true, C2 = false, C3 = true
        Item item3 = createItem("item3", "0123456", 350, 0);
        assertFalse(SILab2.checkCart(Collections.singletonList(item3), 100));

        // Test case 4: C1 = true, C2 = false, C3 = false
        Item item4 = createItem("item4", "1123456", 350, 0);
        assertFalse(SILab2.checkCart(Collections.singletonList(item4), 100));

        // Test case 5: C1 = false, C2 = true, C3 = true
        Item item5 = createItem("item5", "0123456", 250, 0.1f);
        assertTrue(SILab2.checkCart(Collections.singletonList(item5), 100));

        // Test case 6: C1 = false, C2 = true, C3 = false
        Item item6 = createItem("item6", "1123456", 250, 0.1f);
        assertTrue(SILab2.checkCart(Collections.singletonList(item6), 100));

        // Test case 7: C1 = false, C2 = false, C3 = true
        Item item7 = createItem("item7", "0123456", 250, 0);
        assertTrue(SILab2.checkCart(Collections.singletonList(item7), 100));

        // Test case 8: C1 = false, C2 = false, C3 = false
        Item item8 = createItem("item8", "1123456", 250, 0);
        assertTrue(SILab2.checkCart(Collections.singletonList(item8), 100));
    }
}