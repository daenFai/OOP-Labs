package Labwork1;
import java.util.Vector;

public class DragonLaunch {

    private final Vector<Person> people = new Vector<>();

    public void kidnap(Person p) {
        people.add(p);
    }

    public boolean willDragonEatOrNot() {
        int k = 0;

        for (int i = 0; i < people.size(); i++) {
            Person cur = people.get(i);

            if (k > 0 && people.get(k - 1).getGender() == Gender.BOY && cur.getGender() == Gender.GIRL) {
                k--;
            }
            else {
                people.set(k, cur);
                k++;
            }
        }

        people.setSize(k);
        return k != 0;
    }

    public String line() {
        StringBuilder sb = new StringBuilder();
        for (Person p : people) sb.append(p);
        return sb.toString();
    }

    public static DragonLaunch fromString(String s) {
        DragonLaunch dl = new DragonLaunch();
        for (char ch : s.toCharArray()) {
            if (ch == 'B') dl.kidnap(new Person(Gender.BOY));
            else if (ch == 'G') dl.kidnap(new Person(Gender.GIRL));
            else throw new IllegalArgumentException("Only B or G allowed");
        }
        return dl;
    }

    public static void main(String[] args) {

        DragonLaunch a = DragonLaunch.fromString("BBGG");
        System.out.println("Start: BBGG");
        System.out.println(a.willDragonEatOrNot() ? "Eat" : "No one left");
        System.out.println("End:   " + a.line());
        System.out.println();

        DragonLaunch b = DragonLaunch.fromString("GBGB");
        System.out.println("Start: GBGB");
        System.out.println(b.willDragonEatOrNot() ? "Eat" : "No one left");
        System.out.println("End: " + b.line());
    }
}
