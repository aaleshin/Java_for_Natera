package objects;

import org.json.JSONObject;

public class Triangles {
    private JSONObject triangle;
    private String lengthA;
    private String lengthB;
    private String lengthC;
    private String separatorView;
    private String inputValue;
    private String separatorValue;

    public Triangles() {
        triangle = new JSONObject();
    }

    public void setSide(String separatorValue, String inputValue, String lengthA, String lengthB, String lengthC, String separatorView) {
        this.lengthA = lengthA;
        this.lengthB = lengthB;
        this.lengthC = lengthC;
        this.separatorView = separatorView;
        this.inputValue = inputValue;
        this.separatorValue = separatorValue;
    }

    public JSONObject getTriangle() {
        triangle.put(separatorValue, separatorView);
        triangle.put(inputValue, lengthA + separatorView + lengthB + separatorView + lengthC);
        return this.triangle;
    }
}
