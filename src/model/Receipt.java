package model;

import java.util.List;

public class Receipt {
    public int Id;
    public String CompanyName;
    public String CompanyAddress;
    public String Currency;
    public List<ReceiptItem> Items;

    public Receipt(int id, String companyName, String companyAddress, String currency, List<ReceiptItem> items) {
        Id = id;
        CompanyName = companyName;
        CompanyAddress = companyAddress;
        Currency = currency;
        Items = items;
    }

    public double Total() {
        double total = 0;
        for (ReceiptItem item : Items)
            total += item.Quantity * item.Price;
        return total;
    };
}
