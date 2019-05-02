package Main;

import MyMath.Utils;
import SceneStuff.Edge;
import SceneStuff.Vertex;

import java.awt.*;


public class MyGraphics {
    public static final int MARGIN = 20;
    public static final int INSIDE = 0; // 0000
    public static final int LEFT = 1;   // 0001
    public static final int RIGHT = 2;  // 0010
    public static final int BOTTOM = 4; // 0100
    public static final int TOP = 8;    // 1000

    private static MyCanvas mc;

    public MyGraphics(MyCanvas myCanvas) {
        this.mc = myCanvas;
    }

    public static Edge clip(Edge edge) {
        Vertex from = edge.getFrom();
        Vertex to = edge.getTo();

        int fromCode = vertexCode(from);
        int toCode = vertexCode(to);

        double x = 0;
        double y = 0;

        double x0 = from.xPos();
        double y0 = from.yPos();
        double x1 = to.xPos();
        double y1 = to.yPos();

        double xmax = mc.vw + MARGIN;
        double xmin = MARGIN;
        double ymax = mc.vh + MARGIN;
        double ymin = MARGIN;

        boolean accept = false;

        while (true) {
            if ((fromCode | toCode) == 0) {
                // both points inside window trivially accept
                accept = true;
                break;
            } else if ((fromCode & toCode) > 0) {
                // both points share an outside zone trivially reject
                break;
            } else {
                // at least one endpoint is outside
                int code = (fromCode > 0) ? fromCode : toCode;

                // find the intersection point;
                // slope = (y1 - y0) / (x1 - x0)
                // x = x0 + (1 / slope) * (ym - y0), where ym is ymin or ymax
                // y = y0 + slope * (xm - x0), where xm is xmin or xmax

                // point is above the clip window
                if ((code & TOP) > 0) {
                    y = ymin;
                    x = x0 + (x1 - x0) * (y - y0) / (y1 - y0);
                    // point is below the clip window
                } else if ((code & BOTTOM) > 0) {
                    y = ymax;
                    x = x0 + (x1 - x0) * (y - y0) / (y1 - y0);
                    // point is to the right of clip window
                } else if ((code & RIGHT) > 0) {
                    x = xmax;
                    y = y0 + (y1 - y0) * (x - x0) / (x1 - x0);
                    // point is to the left of clip window
                } else if ((code & LEFT) > 0) {
                    x = xmin;
                    y = y0 + (y1 - y0) * (x - x0) / (x1 - x0);
                }

                // update point and continue
                if (code == fromCode) {
                    x0 = x;
                    y0 = y;
                    fromCode = vertexCode(new Vertex(x0, y0, 0));
                } else {
                    x1 = x;
                    y1 = y;
                    toCode = vertexCode(new Vertex(x1, y1, 0));
                }
            }
        }
        // finished clipping line
        if (accept) {
            return new Edge(new Vertex(x0, y0, 0), new Vertex(x1, y1, 0));
        }

        // line rejected
        return null;
    }

    public static int vertexCode(Vertex v) {
        int code = INSIDE;
        double xmax = mc.vw + MARGIN;
        double xmin = MARGIN;
        double ymax = mc.vh + MARGIN;
        double ymin = MARGIN;

        // to the left of clip window
        if (v.xPos() < xmin) {
            code |= LEFT;
        }
        // to the right of clip window
        else if (v.xPos() > xmax) {
            code |= RIGHT;
        }
        // below the clip window
        if (v.yPos() > ymax) {
            code |= BOTTOM;
        }
        // above the clip window
        else if (v.yPos() < ymin) {
            code |= TOP;
        }

        return code;
    }

    public void refreshSize() {
        mc.setPreferredSize(new Dimension((int) mc.vw + 2 * MARGIN, (int) mc.vh + 2 * MARGIN));
    }

    public void refreshColor(){
        mc.frameCol = Utils.get_random_color();
        mc.shapeCol = Utils.get_random_color();
    }

    public void updateWindow(int width, int height) {
        mc.vw = width - 2 * MARGIN;
        mc.vh = height - 2 * MARGIN;

        // update data according resize
        mc.view.updateWindow((int) mc.vw, (int) mc.vh);

        // create updated MV2
        mc.createMV2();

        mc.repaint();
    }

}
