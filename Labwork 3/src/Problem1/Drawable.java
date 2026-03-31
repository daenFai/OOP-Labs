package Problem1;

public interface Drawable {
    void draw();
    void resize(double factor);

    default void highlight(){
        System.out.println("highlighting " + this.getClass().getSimpleName());
    }
}

