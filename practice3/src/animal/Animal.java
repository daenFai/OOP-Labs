package animal;

public class Animal {

    private String name;

    public Animal() {
        System.out.println("Animal created");
    }

    public Animal(String name) {
        this.name = name;
        System.out.println("Animal with name created");
    }

    public void makeSound() {
        System.out.println("Animal makes a sound");
    }

    public void eat() {
        System.out.println("Animal eats");
    }

    // Method to overload
    public void eat(String food) {
        System.out.println("Animal eats " + food);
    }

    public String toString() {
        return "Animal[name=" + name + "]";
    }
}