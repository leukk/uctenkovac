import java.util.List;
import java.util.Scanner;

import model.CommandType;
import model.Receipt;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static final String PROGRAM_NAME = "Uctenkovac";
    public static final String VERSION = "1.0.1";
    public static final Scanner sc = new Scanner(System.in);
    private static Receipt receipt;

    public static void main(String[] args) {
        System.out.println(PROGRAM_NAME + " v" + VERSION);
        System.out.println("Zadejte prikaz ("
                + CommandType.LOAD.getName() + ", "
                + CommandType.SAVE.getName() + ", "
                + CommandType.PRINT.getName() + ", "
                + CommandType.CREATE.getName() + ", "
                + CommandType.EXIT.getName() + "):");

        while (true) {
            try {
                System.out.print("> ");

                List<String> commandArgs = List.of(sc.nextLine().trim().split("\\s+"));
                if (commandArgs.get(0).isEmpty()) {
                    System.out.println("Zadejte prikaz a jeho argumenty");
                    continue;
                }

                CommandType command = CommandType.fromString(commandArgs.get(0));
                switch (command) {
                    case LOAD:
                        EnsureArgumentDefined(commandArgs, 1);
                        receipt = CsvLoader.LoadReceipt(commandArgs.get(1));
                        System.out.println("Nacteno");
                        break;
                    case SAVE:
                        EnsureReceiptDefined(receipt);
                        EnsureArgumentDefined(commandArgs, 1);
                        CsvLoader.SaveReceipt(receipt, commandArgs.get(1));
                        System.out.println("Ulozeno");
                        break;
                    case PRINT:
                        EnsureReceiptDefined(receipt);
                        ReceiptPrinter.PrintReceipt(receipt);
                        break;
                    case CREATE:
                        receipt = ReceiptCreator.CreateReceipt(sc);
                        System.out.println("Vytvoreno");
                        break;
                    case EXIT:
                        return;
                    default:
                        System.out.println("Neznamy prikaz");
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void EnsureArgumentDefined(List<String> args, int index) {
        if (index >= args.size() || args.get(index).isEmpty())
            throw new IllegalArgumentException("Prikaz vyzaduje argument");
    }

    public static void EnsureReceiptDefined(Receipt receipt) {
        if (receipt == null)
            throw new IllegalStateException("Neni nactena uctenka");
    }
}
