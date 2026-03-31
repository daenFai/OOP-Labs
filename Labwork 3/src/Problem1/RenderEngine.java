package Problem1;

import java.util.ArrayList;
import java.util.List;

public class RenderEngine {
    public static void renderAll(List<Drawable> components) {
        for (Drawable d : components) {
            d.draw();
            d.highlight();
        }
    }

    public static void main(String[] args){
        List<Drawable> ui = List.of(new Circle(5),new Button("Submit", 100), new TextLabel("Hi", 14));
        renderAll(ui);
    }
}
