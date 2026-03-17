class Student extends Person implements CanHavePizza, CanHaveRetake, CanHaveParty, Movable {

    public Student(String name) {
        super(name);
    }

    @Override
    public void eatPizza() {
        System.out.println(name + " is eating pizza");
    }

    @Override
    public void retakeExam() {
        System.out.println(name + " is retaking exam");
    }

    @Override
    public void party() {
        System.out.println(name + " is partying");
    }

    @Override
    public void move() {
        System.out.println(name + " is moving");
    }
}
