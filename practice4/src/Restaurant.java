class Restaurant {

    public void servePizza(CanHavePizza eater) {
        eater.eatPizza();

        if (eater instanceof Person) {
            System.out.println("Processing payment");
        }

//        return true;
    }
}