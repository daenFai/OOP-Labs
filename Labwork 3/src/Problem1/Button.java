package Problem1;

public class Button implements Drawable {
    private String label;
    private double width;

    public Button(String label, double width){
        this.label = label;
        this.width = width;
    }

    @Override
    public void draw(){
        System.out.println("Drawing button [" + label + "]");
    }

    @Override
    public void resize(double f){
        width *= f;
    }
}
