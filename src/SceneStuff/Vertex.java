package SceneStuff;

import MyMath.Vector;

public class Vertex extends Vector {
    private int index;

    public Vertex(double xPos, double yPos, double zPos) {
        super(xPos, yPos, zPos);
        this.index = -1;
    }

    public Vertex(double xPos, double yPos, double zPos, int index) {
        super(xPos, yPos, zPos);
        this.index = index;
    }

    public Vertex(String xPos, String yPos, String zPos, int index) {
        super(Double.parseDouble(xPos), Double.parseDouble(yPos), Double.parseDouble(zPos));
        this.index = index;
    }

    public Vertex(String xPos, String yPos, String zPos) {
        this(xPos, yPos, zPos, -1);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return super.toString() + "[" + this.index + "]";
    }
}