import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Receipt;
import model.ReceiptItem;

public class ReceiptCreator {
    public static Receipt CreateReceipt(Scanner sc) {
        int id = PromptInt(sc, "Id");
        String companyName = Prompt(sc, "Nazev firmy");
        String companyAddress = Prompt(sc, "Adresa firmy");
        String currency = Prompt(sc, "Mena");

        List<ReceiptItem> items = new ArrayList<>();
        System.out.println("Zadejte polozky (prazdny nazev ukonci zadavani):");
        while (true) {
            System.out.print("Nazev (text): ");
            String name = sc.nextLine().trim();
            if (name.isEmpty())
                break;
            int quantity = PromptInt(sc, "Mnozstvi");
            double price = PromptDouble(sc, "Cena");
            items.add(new ReceiptItem(name, quantity, price));
        }

        return new Receipt(id, companyName, companyAddress, currency, items);
    }

    private static String Prompt(Scanner sc, String label) {
        return Prompt(sc, label, "text");
    }

    private static String Prompt(Scanner sc, String label, String type) {
        System.out.print(label + " (" + type + "): ");
        String value = sc.nextLine().trim();
        if (value.isEmpty())
            throw new IllegalArgumentException(label + " nesmi byt prazdne");
        return value;
    }

    private static int PromptInt(Scanner sc, String label) {
        while (true) {
            String value = Prompt(sc, label, "cele cislo");
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("Neplatny typ: '" + value + "' neni cele cislo.");
            }
        }
    }

    private static double PromptDouble(Scanner sc, String label) {
        while (true) {
            String value = Prompt(sc, label, "desetinne cislo");
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                System.out.println("Neplatny typ: '" + value + "' neni desetinne cislo.");
            }
        }
    }
}
