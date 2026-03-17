class Person {
    protected String name;

    public Person(String name) {
        this.name = name;
    }

    public void eat() {
        System.out.println(name + " is eating");
    }
}