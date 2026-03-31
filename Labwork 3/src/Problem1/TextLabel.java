package Problem1;

public class TextLabel implements Drawable {

    private String text;
    private double fontSize;

    public TextLabel(String t, double fs) {
        this.text = t;
        this.fontSize = fs;
    }

    @Override
    public void draw() {
        System.out.println("Drawing text: " + text);
    }

    @Override
    public void resize(double f) {
        fontSize *= f;
    }
}
