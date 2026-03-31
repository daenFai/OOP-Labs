package Problem1;

public abstract class Animal {
    String name;

    void sleep(){
        System.out.println(name + " sleeping");
    }

    abstract void speak();
}
