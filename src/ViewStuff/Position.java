package ViewStuff;

import SceneStuff.Vertex;

public class Position extends Vertex {
    public Position(String xPos, String yPos, String zPos) {
        super(Double.parseDouble(xPos), Double.parseDouble(yPos), Double.parseDouble(zPos));
    }
}
