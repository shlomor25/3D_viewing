package ViewStuff;

public class Window {
    private double left;
    private double right;
    private double bottom;
    private double top;

    public Window(String left, String right, String bottom, String top) {
        this.left = Double.parseDouble(left);
        this.right = Double.parseDouble(right);
        this.bottom = Double.parseDouble(bottom);
        this.top = Double.parseDouble(top);
    }

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }

    public double getBottom() {
        return bottom;
    }

    public double getTop() {
        return top;
    }
}
