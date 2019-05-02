package ViewStuff;

public class ViewPort {
    private int width;
    private int height;

    public ViewPort(String vw, String vh) {
        this.width = Integer.parseInt(vw);
        this.height = Integer.parseInt(vh);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}

