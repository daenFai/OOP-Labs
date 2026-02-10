package Labwork1;
import java.util.*;

public class Analyzer {
    public static void main(String[] args) {

        Data data = new Data();
        String input = "";
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.print("Enter number (Q to quit): ");
            input = scanner.nextLine();
            try {
                if(input.equals("Q")) break;
                double num = Double.parseDouble(input);
                data.add(num);
            }
            catch (NumberFormatException e) {
                System.err.println("Invalid string format: " + e.getMessage());
            }
        }
        System.out.println("Average is " + data.getAverage());
        System.out.println("Max is " + data.getMax());

        scanner.close();
    }
}
