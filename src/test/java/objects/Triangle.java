package objects;

import java.util.Map;
import org.json.JSONObject;

public class Triangle {
    private JSONObject triangle;
    private int lengthA;
    private int lengthB;
    private int lengthC;
    private String separatorView;
    private String inputValue;
    private String separatorValue;

    public Triangle() {
        triangle = new JSONObject();
    }

    public void setSide(String separatorValue, String inputValue, int lengthA, int lengthB, int lengthC, String separatorView) {
        this.lengthA = lengthA;
        this.lengthB = lengthB;
        this.lengthC = lengthC;
        this.separatorView = separatorView;
        this.inputValue = inputValue;
        this.separatorValue = separatorValue;
    }

    public JSONObject getTriangle() {
        triangle.put(separatorValue, separatorView);
        triangle.put(inputValue, Integer.toString(lengthA) + separatorView + Integer.toString(lengthB) + separatorView + Integer.toString(lengthC));
        return this.triangle;
    }

}
