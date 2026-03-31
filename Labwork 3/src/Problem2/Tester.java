package Problem2;

public class Tester {
    public static void main(String[] args){
        Car car = new Car(50);
        car.move();
        Plane plane = new Plane(300,900);
        plane.move();
        plane.fly();
    }
}
