import java.util.*;

public class task1 {
        public static void main(String[] args) {
            Scanner input = new Scanner(System.in);

            System.out.print("enter your name: ");
            String name = input.next();

            String line = "-";
            line = line.repeat(name.length());

            System.out.println("+" + line + "+");
            System.out.println("|" + name + "|");
            System.out.println("+" + line + "+");

            input.close();
        }
}
