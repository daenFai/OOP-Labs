package Problem4;

import java.util.*;

public class Test {
    public static void main(String[] args) {

        Employee e1 = new Employee("Alice", 5000, new Date(), "A1");
        Employee e2 = new Employee("Bob", 7000, new Date(), "B1");

        Manager m1 = new Manager("Charlie", 7000, new Date(), "C1", 1000);
        m1.addEmployee(e1);

        System.out.println(e1);
        System.out.println(m1);

        // equals
        System.out.println(e1.equals(e2));

        // compareTo
        System.out.println(e1.compareTo(e2));

        // sorting
        Vector<Employee> list = new Vector<>();
        list.add(e1);
        list.add(e2);
        list.add(m1);

        Collections.sort(list);
        System.out.println(list);

        // Comparator
        list.sort(new NameComparator());
        System.out.println(list);

        // clone
        Employee clone = e1.clone();
        System.out.println(clone);
    }
}
