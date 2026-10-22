import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Receipt;
import model.ReceiptItem;

public class ReceiptCreator {
    public static Receipt CreateReceipt(Scanner sc) {
        int id = Integer.parseInt(Prompt(sc, "Id"));
        String companyName = Prompt(sc, "Nazev firmy");
        String companyAddress = Prompt(sc, "Adresa firmy");
        String currency = Prompt(sc, "Mena");

        List<ReceiptItem> items = new ArrayList<>();
        System.out.println("Zadejte polozky (prazdny nazev ukonci zadavani):");
        while (true) {
            System.out.print("Nazev: ");
            String name = sc.nextLine().trim();
            if (name.isEmpty())
                break;
            int quantity = Integer.parseInt(Prompt(sc, "Mnozstvi"));
            double price = Double.parseDouble(Prompt(sc, "Cena"));
            items.add(new ReceiptItem(name, quantity, price));
        }

        return new Receipt(id, companyName, companyAddress, currency, items);
    }

    private static String Prompt(Scanner sc, String label) {
        System.out.print(label + ": ");
        String value = sc.nextLine().trim();
        if (value.isEmpty())
            throw new IllegalArgumentException(label + " nesmi byt prazdne");
        return value;
    }
}
