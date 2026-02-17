package animal;

public class test1 {
    public static void main(String[] args) {
        Animal a1 = new Animal();
        a1.makeSound();          // Animal makes a sound
        a1.eat();                // Animal eats
        a1.eat("meat");          // Animal eats meat
        System.out.println(a1);  // Animal[name=null]

        Dog d1 = new Dog();
        d1.makeSound();           // Bark!
        d1.makeSound(3);          // Bark! Bark! Bark!
        d1.eat("bone");           // Animal eats bone
        System.out.println(d1);   // Dog[Animal[name=null], breed=null]

        Dog d2 = new Dog("Buddy", "Labrador");
        System.out.println(d2);   // Dog[Animal[name=Buddy], breed=Labrador]
    }
}
