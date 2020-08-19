package objects;

import org.json.JSONObject;

public class Triangles {
    private JSONObject triangle;
    private float lengthA;
    private float lengthB;
    private float lengthC;
    private float lengthD;
    private String separatorView;
    private String inputValue;
    private String separatorValue;

    public Triangles() {
        triangle = new JSONObject();
    }

    public void setSide(String separatorValue, String inputValue, float lengthA, float lengthB, float lengthC, String separatorView) {
        this.lengthA = lengthA;
        this.lengthB = lengthB;
        this.lengthC = lengthC;
        this.separatorView = separatorView;
        this.inputValue = inputValue;
        this.separatorValue = separatorValue;
    }

    public JSONObject getTriangle() {
        triangle.put(separatorValue, separatorView);
        triangle.put(inputValue, Float.toString(lengthA) + separatorView + Float.toString(lengthB) + separatorView + Float.toString(lengthC));
        return this.triangle;
    }

    public void setSquareSide(String separatorValue, String inputValue, float lengthA, float lengthB, float lengthC, float lengthD, String separatorView) {
        this.lengthA = lengthA;
        this.lengthB = lengthB;
        this.lengthC = lengthC;
        this.lengthD = lengthD;
        this.separatorView = separatorView;
        this.inputValue = inputValue;
        this.separatorValue = separatorValue;
    }

    public JSONObject getSquare() {
        triangle.put(separatorValue, separatorView);
        triangle.put(inputValue, Float.toString(lengthA) + separatorView + Float.toString(lengthB) + separatorView + Float.toString(lengthC) + separatorView + Float.toString(lengthD));
        return this.triangle;
    }

}
