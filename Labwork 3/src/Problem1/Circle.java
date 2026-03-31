package Problem1;

public class Circle implements Drawable {
    double radius;
    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public void draw(){
        System.out.println("Drawing circle r=" + radius);
    }

    @Override
    public void resize(double f){
        radius *= f;
    }
}
