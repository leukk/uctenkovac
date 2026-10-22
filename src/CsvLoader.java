import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import model.Receipt;
import model.ReceiptItem;

public class CsvLoader {
    private static final String SEP = ";";

    public static Receipt LoadReceipt(String path) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(path));
        if (lines.isEmpty())
            throw new IllegalArgumentException("Empty file: " + path);

        String[] head = split(lines.get(0), 4);
        List<ReceiptItem> items = new ArrayList<>();
        for (String line : lines.subList(1, lines.size())) {
            if (line.isBlank())
                continue;
            String[] f = split(line, 3);
            items.add(new ReceiptItem(f[0], Integer.parseInt(f[1]), Double.parseDouble(f[2])));
        }
        return new Receipt(Integer.parseInt(head[0]), head[1], head[2], head[3], items);
    }

    public static void SaveReceipt(Receipt receipt, String path) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add(join(receipt.Id, receipt.CompanyName, receipt.CompanyAddress, receipt.Currency));
        for (ReceiptItem item : receipt.Items)
            lines.add(join(item.Name, item.Quantity, item.Price));
        Files.write(Path.of(path), lines);
    }

    private static String[] split(String line, int expected) {
        String[] fields = line.split(SEP, -1);
        if (fields.length != expected)
            throw new IllegalArgumentException("Expected " + expected + " fields: " + line);
        return fields;
    }

    private static String join(Object... values) {
        List<String> parts = new ArrayList<>();
        for (Object v : values) {
            String s = String.valueOf(v);
            if (s.contains(SEP) || s.contains("\n"))
                throw new IllegalArgumentException("Value contains '" + SEP + "' or newline: " + s);
            parts.add(s);
        }
        return String.join(SEP, parts);
    }
}
