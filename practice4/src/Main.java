public class Main {
    public static void main(String[] args) {

        Restaurant restaurant = new Restaurant();

        Cat cat = new Cat();
        Student student = new Student("Zhalgas");

        restaurant.servePizza(cat);
        restaurant.servePizza(student);
        student.when();
    }
}
