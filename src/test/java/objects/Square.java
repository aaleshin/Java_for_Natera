package objects;

import org.json.JSONObject;

public class Square {
    private JSONObject triangle;
    private String lengthA;
    private String lengthB;
    private String lengthC;
    private String lengthD;
    private String separatorView;
    private String inputValue;
    private String separatorValue;

    public Square() {
        triangle = new JSONObject();
    }

    public void setSquareSide(String separatorValue, String inputValue, String lengthA, String lengthB, String lengthC, String lengthD, String separatorView) {
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
        triangle.put(inputValue, lengthA + separatorView + lengthB + separatorView + lengthC + separatorView + lengthD);
        return this.triangle;
    }
}
