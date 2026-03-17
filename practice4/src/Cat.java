class Cat extends Animal implements CanHavePizza {

    @Override
    public void eatPizza() {
        System.out.println("Cat is eating pizza");
    }
}