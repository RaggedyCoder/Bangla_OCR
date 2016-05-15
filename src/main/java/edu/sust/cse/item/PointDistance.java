package edu.sust.cse.item;

/**
 * After Using the <b>Canny Algorithm</b> to detect the <b>Edge</b> in a Document
 *
 */
public class PointDistance {
    private int horizontalValue;
    private int verticalValue;

    public PointDistance() {
        this(0, 0);
    }

    public PointDistance(int horizontalValue, int verticalValue) {
        this.horizontalValue = horizontalValue;
        this.verticalValue = verticalValue;
    }

    public int getHorizontalValue() {
        return horizontalValue;
    }

    public void setHorizontalValue(int horizontalValue) {
        this.horizontalValue = horizontalValue;
    }

    public int getVerticalValue() {
        return verticalValue;
    }

    public void setVerticalValue(int verticalValue) {
        this.verticalValue = verticalValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PointDistance)) return false;

        PointDistance that = (PointDistance) o;

        return horizontalValue == that.horizontalValue && verticalValue == that.verticalValue;

    }

    @Override
    public int hashCode() {
        int result = horizontalValue;
        result = 31 * result + verticalValue;
        return result;
    }

    @Override
    public String toString() {
        return "PointDistance{" +
                "horizontalValue=" + horizontalValue +
                ", verticalValue=" + verticalValue +
                '}';
    }
}
