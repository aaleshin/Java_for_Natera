package objects;

import java.util.HashMap;
import java.util.Map;

public class Triangle {
    private Map<String, String> triangle;

    public Triangle() {
        triangle = new HashMap<>();
    }

    public void setSide(String inputValue, float lengthA, float lengthB, float lengthC, String separatorView) {
        triangle.put(inputValue, Float.toString(lengthA) + separatorView + Float.toString(lengthB) + separatorView + Float.toString(lengthC));
    }

//    public void setSideB(int lengthB) {
//        triangle.put("input", Integer.toString(lengthB));
//    }
//
//    public void setSideC(int lengthC) {
//        triangle.put("input", Integer.toString(lengthC));
//    }

//    public void setSeparator(String separatorView) {
//        triangle.put("separator", separatorView);
//    }
}
