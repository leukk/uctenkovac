package model;

public class ReceiptItem {
    public String Name;
    public int Quantity;
    public double Price;

    public ReceiptItem(String name, int quantity, double price) {
        Name = name;
        Quantity = quantity;
        Price = price;
    }
}
