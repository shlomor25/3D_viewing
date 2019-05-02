package Main;

import java.awt.*;
import java.awt.event.*;


public class Main {
    public static Frame myFrame;

    public static void main(String[] args) {
        // gui
        myFrame = new Frame("Exercise1");
        MyCanvas myCanvas = new MyCanvas();

        myFrame.add(myCanvas);
        myFrame.pack();
        myFrame.setVisible(true);
        myCanvas.requestFocus();

        myFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        myCanvas.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                myCanvas.mg.updateWindow(myCanvas.getWidth(), myCanvas.getHeight());
            }
        });
    }
}
