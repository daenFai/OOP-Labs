import java.util.*;

public class task5 {
        public static void main(String[] args) {
            Scanner input = new Scanner(System.in);
            System.out.print("enter your balance: ");
            double balance = input.nextDouble();
            System.out.print("enter your interest rate: ");
            double interest = input.nextDouble();

            double new_balance = balance + (interest/100)*balance;
            System.out.println("Your new balance is: " + new_balance);

            input.close();
        }
}
