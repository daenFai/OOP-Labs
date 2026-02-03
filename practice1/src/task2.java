import java.util.*;
//package javalang

public class task2 {
        public static void main(String[] args) {
            Scanner input = new Scanner(System.in);

            System.out.print("length of one side: ");
            int a = input.nextInt();
            double b = a;

            System.out.println("area: " + a*a);
            System.out.println("perimeter: " + 4*a);
            System.out.println("length of diagonal: " + Math.sqrt(2)*b);

            input.close();
        }
}
