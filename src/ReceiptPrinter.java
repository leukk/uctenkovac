import model.Receipt;
import model.ReceiptItem;

public class ReceiptPrinter {
    private static final int WIDTH = 40; // inner width between the borders

    public static void PrintReceipt(Receipt receipt) {
        String border = "+" + "-".repeat(WIDTH) + "+";
        String separator = "|" + "-".repeat(WIDTH) + "|";

        System.out.println(border);
        System.out.println(center(receipt.CompanyName));
        System.out.println(center(receipt.CompanyAddress));
        System.out.println(center("Receipt #" + receipt.Id));
        System.out.println(separator);

        for (ReceiptItem item : receipt.Items) {
            String left = item.Quantity + "x " + item.Name;
            String right = String.format("%.2f", item.Quantity * item.Price);
            System.out.println(row(left, right));
        }

        System.out.println(separator.replace('-', '='));
        System.out.println(row("TOTAL", String.format("%.2f %s", receipt.Total(), receipt.Currency)));
        System.out.println(border);
        System.out.println(center("Thank you!"));
        System.out.println(border);
    }

    private static String center(String text) {
        text = fit(text, WIDTH);
        int left = (WIDTH - text.length()) / 2;
        return "|" + " ".repeat(left) + text + " ".repeat(WIDTH - left - text.length()) + "|";
    }

    // left text padded so right text is aligned to the right border
    private static String row(String left, String right) {
        left = fit(left, WIDTH - right.length() - 3);
        return "| " + left + " ".repeat(WIDTH - 2 - left.length() - right.length()) + right + " |";
    }

    private static String fit(String text, int max) {
        if (text == null)
            return "";
        return text.length() <= max ? text : text.substring(0, max - 3) + "...";
    }
}
