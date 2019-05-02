package ViewStuff;

import SceneStuff.Vertex;

public class LookAt extends Vertex {
    public LookAt(String xPos, String yPos, String zPos) {
        super(Double.parseDouble(xPos), Double.parseDouble(yPos), Double.parseDouble(zPos));
    }
}