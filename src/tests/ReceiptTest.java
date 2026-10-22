package tests;

import java.util.ArrayList;
import java.util.List;

import model.Receipt;
import model.ReceiptItem;
import static org.junit.jupiter.api.Assertions.*;

class ReceiptTest {

    @org.junit.jupiter.api.Test
    void total() {
        List<ReceiptItem> items = new ArrayList<>();
        items.add(new ReceiptItem("Item A", 2, 10.5));  // 21.0
        items.add(new ReceiptItem("Item B", 1, 5.0));   // 5.0
        items.add(new ReceiptItem("Item C", 3, 2.25));  // 6.75

        Receipt receipt = new Receipt(1, "Test Company", "Test Address", "EUR", items);

        assertEquals(32.75, receipt.Total(), 0.001);
    }
}