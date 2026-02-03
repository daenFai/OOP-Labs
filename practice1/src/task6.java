import java.util.*;

public class task6 {
        public static void main(String[] args) {
            Scanner input = new Scanner(System.in);
            System.out.print("Enter a string: ");
            String s1 = input.nextLine();

            String s2 = new StringBuilder(s1).reverse().toString();
            if(s1.equals(s2)){
                System.out.println("Palindrome");
            }
            else {
                System.out.println("Not Palindrome");
            }

            input.close();
        }
}
