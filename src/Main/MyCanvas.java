package Main;

import MyMath.Matrix;
import MyMath.Utils;
import MyMath.Vector;
import SceneStuff.Edge;
import SceneStuff.Scene;
import ViewStuff.LookAt;
import ViewStuff.Position;
import ViewStuff.Up;
import ViewStuff.View;

import java.awt.*;
import java.awt.event.*;
import java.util.List;


public class MyCanvas extends Canvas implements MouseListener, MouseMotionListener, KeyListener {
    public static final long serialVersionUID = 1L;

    MyGraphics mg;

    Scene scene;
    View view;

    Matrix AT;  // all current transformations
    Matrix CT;  // current transformation
    Matrix MV1; // from world to viewer
    Matrix MV2; // normalize scale
    Matrix P;   // projection
    Matrix TT;  // total transformation
    Matrix LT;  // last transformation
    Matrix TM1; // transformation to viewer origin
    Matrix TM2; // transformation from viewer origin

    // options' status
    boolean isTranslate;
    boolean isScale;
    boolean isRotate;
    boolean isClip;
    char rotationAxis;

    // size
    double r, l, b, t;
    double vw, vh;

    // init vw, vh
    double ivw, ivh;

    // start and end points of mouse
    Point pStart, pEnd;

    // colors
    Color frameCol;
    Color shapeCol;

    // text to draw
    String status;

    // scn and viw names
    String viwName;
    String scnName;

    // viw scn didn't change
    boolean isSameViwScn;

    public void load() {
        try {
            if (!this.isSameViwScn) {
                // load new scn and viw
                this.view.loadViw(this.viwName);
                this.scene.loadScn(this.scnName);
                this.isSameViwScn = true;
            }

            // size
            this.l = this.view.getWindow().getLeft();
            this.r = this.view.getWindow().getRight();
            this.b = this.view.getWindow().getBottom();
            this.t = this.view.getWindow().getTop();
            this.ivw = this.vw = this.view.getViewPort().getWidth();
            this.ivh = this.vh = this.view.getViewPort().getHeight();

            // MyGraphics
            this.mg = new MyGraphics(this);
            this.mg.refreshSize();

            // init trans
            this.AT = Utils.identity();
            this.CT = Utils.identity();
            this.TT = Utils.identity();
            this.LT = Utils.identity();
            this.TM1 = Utils.identity();
            this.TM2 = Utils.identity();

            // random colors
            this.mg.refreshColor();

            // calc MV1
            Position position = this.view.getPosition();
            LookAt lookAt = this.view.getLookAt();
            Up up = this.view.getUp();
            this.MV1 = Utils.world_to_viewer(position, lookAt, up);

            // calc projection trans
            this.P = Utils.projection();

            // create MV2
            this.createMV2();

            Main.myFrame.pack();

        } catch (Exception e) {
        }
    }

    public MyCanvas() {
        // listeners
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);

        // view and scene
        this.scnName = "ex1.scn";
        this.viwName = "ex1.viw";
        this.view = new View();
        this.scene = new Scene();

        // init vars
        this.isClip = true;
        this.isScale = false;
        this.isRotate = false;
        this.isTranslate = false;
        this.rotationAxis = 'z';
        this.status = "rotation axis is " + this.rotationAxis;
        this.isSameViwScn = false;

        this.load();
    }

    public void createMV2() {
        // translate to origin trans
        Matrix T1 = Utils.translate(-(l + (r - l) / 2), -(b + (t - b) / 2), 0);

        // normalize scale trans
        Matrix S = Utils.scale(vw / (r - l), vh / (t - b), 0);

        // translate from origin trans
        Matrix T2 = Utils.translate(mg.MARGIN + vw / 2, mg.MARGIN + vh / 2, 0);

        // calc final normalize scale trans
        this.MV2 = T2.mult(S).mult(T1);
    }

    public void paint(Graphics g) {
        // print frame
        g.setColor(this.frameCol);
        g.drawRect(mg.MARGIN, mg.MARGIN, (int) this.vw, (int) this.vh);

        // calc final trans
        this.TT = this.MV2.mult(this.P).mult(this.CT).mult(this.AT).mult(this.MV1);

        // apply final trans over edges' vertices
        List<Edge> edgeList = this.scene.apply(this.TT);

        // print edges
        g.setColor(this.shapeCol);
        for (Edge e : edgeList) {
            Edge newEdge = e;

            if (this.isClip) {
                newEdge = mg.clip(e);

                // edge rejected
                if (newEdge == null) {
                    continue;
                }
            }

            int fromX = (int) newEdge.getFrom().xPos();
            int fromY = (int) newEdge.getFrom().yPos();
            int toX = (int) newEdge.getTo().xPos();
            int toY = (int) newEdge.getTo().yPos();
            g.drawLine(fromX, fromY, toX, toY);
        }

        g.setColor(Color.black);
        g.setFont(new Font("TimesRoman", Font.BOLD, (int) (0.75 * mg.MARGIN)));
        g.drawString(this.status, (int) (1.25 * mg.MARGIN), (int) (0.75 * mg.MARGIN));

        // init cur trans
        this.CT = Utils.identity();
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        char charPressed = keyEvent.getKeyChar();
        //System.out.println("key pressed: " + charPressed);

        // hebrew to english
        switch (charPressed) {
            case '/':
                charPressed = 'q';
                break;
            case 'ב':
                charPressed = 'c';
                break;
            case 'ר':
                charPressed = 'r';
                break;
            case 'ך':
                charPressed = 'l';
                break;
            case 'ס':
                charPressed = 'x';
                break;
            case 'ט':
                charPressed = 'y';
                break;
            case 'ז':
                charPressed = 'z';
                break;
        }

        // convert upper to lower
        charPressed = Character.toLowerCase(charPressed);

        // handle char pressed
        switch (charPressed) {
            // q for quit
            case 'q':
                System.exit(0);
                break;
            // c for clip
            case 'c': {
                this.isClip = !this.isClip;
                if (this.isClip) {
                    this.status = "clip is on";
                } else {
                    this.status = "clip is off";
                }
                break;
            }
            // r for restart
            case 'r': {
                this.status = "restart pressed";
                this.load();
                break;
            }
            // l for load
            case 'l': {
                try {
                    FileDialog fd = new FileDialog(Main.myFrame, "choose scene or view");
                    fd.setVisible(true);
                    String f = fd.getFile();
                    this.isSameViwScn = false;
                    if (f.endsWith(".scn")) {
                        this.scnName = f;
                    } else if (f.endsWith(".viw")) {
                        this.viwName = f;
                    }
                    this.load();
                } catch (Exception e) {
                }
                break;
            }
            // x y z for change axis
            case 'x':
            case 'y':
            case 'z': {
                this.rotationAxis = charPressed;
                this.status = "rotation axis is " + this.rotationAxis;
                break;
            }
            default:
                break;
        }
        this.repaint();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        this.pStart = mouseEvent.getPoint();
        //System.out.println("mouse pressed: " + this.pStart);

        // divide frame to three
        double tw = (this.vw + 2 * mg.MARGIN) / 3;
        double th = (this.vh + 2 * mg.MARGIN) / 3;

        int x = this.pStart.x;
        int y = this.pStart.y;

        // the window is divided to:
        // 1 2 3
        // 4 5 6
        // 7 8 9

        // case 5 translate
        if (tw < x && x < 2 * tw && th < y && y < 2 * th) {
            this.isTranslate = true;
            this.isScale = false;
            this.isRotate = false;
            // case 2 4 6 8 scale
        } else if ((tw < x && x < 2 * tw && y < th) ||
                (x < tw && th < y && y < 2 * th) ||
                (2 * tw < x && th < y && y < 2 * th) ||
                (tw < x && x < 2 * tw && 2 * th < y)) {
            this.isTranslate = false;
            this.isScale = true;
            this.isRotate = false;
            // case 1 3 7 9 rotate
        } else if ((x < tw && y < th) ||
                (2 * tw < x && y < th) ||
                (x < tw && 2 * th < y) ||
                (2 * tw < x && 2 * th < y)) {
            this.isTranslate = false;
            this.isScale = false;
            this.isRotate = true;
        }
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        // end point of mouse
        this.pEnd = mouseEvent.getPoint();

        // case pressed translate region
        if (this.isTranslate) {
            this.draggedTranslate(mouseEvent);

            // case pressed rotate region
        } else if (this.isRotate) {
            this.draggedRotate(mouseEvent);

            // case pressed scale region
        } else if (this.isScale) {
            this.draggedScale(mouseEvent);
        }

        // save as last transformation
        this.LT = this.CT;

        this.repaint();
    }

    public void createTranslations() {
        Matrix m = Matrix.mult(MV1, Utils.vectorToMatrix(Utils.vertexToVector(view.getLookAt())));
        Vector v = Utils.matrixToVector(m);
        this.TM1 = Utils.translate(-v.xPos(), -v.yPos(), -v.zPos());
        this.TM2 = Utils.translate(v.xPos(), v.yPos(), v.zPos());
    }

    public void draggedRotate(MouseEvent mouseEvent) {
        // center frame x and y
        double centerY = (this.vh + 2 * mg.MARGIN) / 2;
        double centerX = (this.vw + 2 * mg.MARGIN) / 2;

        // vectors of end and start point sub center frame
        Vector vd = new Vector(this.pEnd.x - centerX, this.pEnd.y - centerY, 0);
        Vector vs = new Vector(this.pStart.x - centerX, this.pStart.y - centerY, 0);

        // calc angles of vectors and sub
        double as = Math.atan2(vd.xPos(), vd.yPos());
        double ad = Math.atan2(vs.xPos(), vs.yPos());
        double angle = ad - as;

        // create
        this.createTranslations();

        // handle according axis
        Matrix R;
        switch (this.rotationAxis) {
            case 'x':
                R = Utils.rotateX(angle);
                break;
            case 'y':
                R = Utils.rotateY(angle);
                break;
            case 'z':
                R = Utils.rotateZ(angle);
                break;
            default:
                R = Utils.identity();
                break;
        }

        this.CT = this.TM2.mult(R).mult(this.TM1);
    }

    public void draggedTranslate(MouseEvent mouseEvent) {
        double x = this.pEnd.x - this.pStart.x;
        double y = this.pEnd.y - this.pStart.y;
        double xPixelsPerUnit = vw / (r - l);
        double yPixelsPerUnit = vh / (t - b);
        this.CT = Utils.translate(x / xPixelsPerUnit, y / yPixelsPerUnit, 0);
    }

    public void draggedScale(MouseEvent mouseEvent) {
        this.createTranslations();
        double centerX = (this.vw + 2 * mg.MARGIN) / 2;
        double centerY = (this.vh + 2 * mg.MARGIN) / 2;
        Vector vs = new Vector(this.pStart.x - centerX, this.pStart.y - centerY, 0);
        Vector vd = new Vector(this.pEnd.x - centerX, this.pEnd.y - centerY, 0);
        double prop = vd.magnitude() / vs.magnitude();
        CT = TM2.mult(Utils.scale(prop, prop, prop)).mult(TM1);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        this.AT = this.LT.mult(this.AT);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }
}