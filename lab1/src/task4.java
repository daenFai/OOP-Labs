import java.util.*;

public class task4 {
        public static void main(String[] args) {
            Scanner input = new Scanner(System.in);

            int a = input.nextInt();
            int b = input.nextInt();
            int c = input.nextInt();

            double D = b*b - 4*a*c;

            if(D<0)
                System.out.println("error");
            else
                System.out.println(D);

            input.close();
        }
}
