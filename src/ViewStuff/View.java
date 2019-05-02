package ViewStuff;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class View {
    private Position position; // Px Py Pz coordinates of camera (real numbers)
    private LookAt lookAt; // Lx Ly Lz coordinates of the point the camera looks at (real numbers)
    private Up up; // Vx Vy Vz a vector representing the up direction of the camera (real numbers)
    private Window window; // left, right, bottom, and top boundaries of the window (real numbers)
    private ViewPort viewPort; // vw vh width and height of viewport in pixels (integers)

    public void loadViw(String fileName) throws Exception {
        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        String[] crdnts;

        // read position
        if ((line = br.readLine()) != null) {
            crdnts = line.split(" ");
            this.position = new Position(crdnts[1], crdnts[2], crdnts[3]);
        }

        // read lookAt
        if ((line = br.readLine()) != null) {
            crdnts = line.split(" ");
            this.lookAt = new LookAt(crdnts[1], crdnts[2], crdnts[3]);
        }

        // read up
        if ((line = br.readLine()) != null) {
            crdnts = line.split(" ");
            this.up = new Up(crdnts[1], crdnts[2], crdnts[3]);
        }

        // read window
        if ((line = br.readLine()) != null) {
            crdnts = line.split(" ");
            this.window = new Window(crdnts[1], crdnts[2], crdnts[3], crdnts[4]);
        }

        // read viewPort
        if ((line = br.readLine()) != null) {
            crdnts = line.split(" ");
            this.viewPort = new ViewPort(crdnts[1], crdnts[2]);
        }

        br.close();
    }

    public Position getPosition() {
        return this.position;
    }

    public LookAt getLookAt() {
        return this.lookAt;
    }

    public Up getUp() {
        return this.up;
    }

    public Window getWindow() {
        return this.window;
    }

    public ViewPort getViewPort() {
        return this.viewPort;
    }

    public void updateWindow(int width, int height) {
        this.viewPort.setHeight(height);
        this.viewPort.setWidth(width);
    }
}