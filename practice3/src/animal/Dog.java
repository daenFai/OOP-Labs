package animal;

public class Dog extends Animal {

    private String breed;

    // using super() without parameters
    public Dog() {
        super();
        System.out.println("Dog created");
    }

    // using super(name) with parameter
    public Dog(String name, String breed) {
        super(name);
        this.breed = breed;
    }

    // Method overriding
    @Override
    public void makeSound() {
        System.out.println("Bark!");
    }

    // Method overloading (different parameter list)
    public void makeSound(int times) {
        for (int i = 0; i < times; i++) {
            System.out.println("Bark!");
        }
    }

    @Override
    public String toString() {
        return "Dog[" + super.toString() + ", breed=" + breed + "]";
    }
}