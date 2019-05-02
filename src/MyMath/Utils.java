package MyMath;

import SceneStuff.Vertex;

import java.awt.*;
import java.util.Random;

public class Utils {
    public static Matrix identity() {
        double[][] t = {{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}};
        return new Matrix(t);
    }

    public static Matrix translate(double a, double b, double c) {
        double[][] t = {{1, 0, 0, a}, {0, 1, 0, b}, {0, 0, 1, c}, {0, 0, 0, 1}};
        return new Matrix(t);
    }

    public static Matrix T(double a, double b, double c) {
        return translate(a, b, c);
    }

    public static Matrix invT(double a, double b, double c) {
        return T(-a, -b, -c);
    }

    public static Matrix translate(Vector v, double a, double b, double c) {
        return Matrix.mult(T(a, b, c), vectorToMatrix(v));
    }


    public static Matrix translate(Matrix v, double a, double b, double c) {
        return translate(matrixToVector(v), a, b, c);
    }

    public static Matrix scale(double a, double b, double c) {
        double[][] m = {{a, 0, 0, 0}, {0, b, 0, 0}, {0, 0, c, 0}, {0, 0, 0, 1}};
        return new Matrix(m);
    }

    public static Matrix S(double a, double b, double c) {
        return scale(a, b, c);
    }

    public static Matrix R(Vector u, Vector v, Vector w) {
        double[][] m = new double[4][4];
        m[0][0] = u.xPos();
        m[0][1] = u.yPos();
        m[0][2] = u.zPos();
        m[1][0] = v.xPos();
        m[1][1] = v.yPos();
        m[1][2] = v.zPos();
        m[2][0] = w.xPos();
        m[2][1] = w.yPos();
        m[2][2] = w.zPos();
        m[3][3] = 1;
        return new Matrix(m);
    }

    public static Matrix rotateX(double angle) {
        double[][] m = new double[4][4];
        m[1][1] = Math.cos(angle);
        m[1][2] = -Math.sin(angle);
        m[2][1] = Math.sin(angle);
        m[2][2] = Math.cos(angle);
        m[0][0] = 1;
        m[3][3] = 1;
        return new Matrix(m);
    }

    public static Matrix rotateY(double angle) {
        double[][] m = new double[4][4];
        m[0][0] = Math.cos(angle);
        m[0][2] = -Math.sin(angle);
        m[2][0] = Math.sin(angle);
        m[2][2] = Math.cos(angle);
        m[1][1] = 1;
        m[3][3] = 1;
        return new Matrix(m);
    }

    public static Matrix rotateZ(double angle) {
        double[][] m = new double[4][4];
        m[0][0] = Math.cos(angle);
        m[0][1] = -Math.sin(angle);
        m[1][0] = Math.sin(angle);
        m[1][1] = Math.cos(angle);
        m[2][2] = 1;
        m[3][3] = 1;
        return new Matrix(m);
    }

    public static Matrix world_to_viewer(Vertex P, Vertex L, Vector V) {
        // first vector is from camera position to point of look at
        Vector Z = Vector.sub(P, L).normalize();
        // second vector is vertical to the first and up vector
        Vector X = Vector.crossProduct(V, Z).normalize();
        // third vector is vertical to the first and the second
        Vector Y = Vector.crossProduct(Z, X);

        return Matrix.mult(R(X, Y, Z), invT(P.xPos(), P.yPos(), P.zPos()));
    }

    public static Matrix projection() {
        double[][] m = {{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}};
        return new Matrix(m);
    }

    public static Vector matrixToVector(Matrix M) {
        double vx = M.getM()[0][0];
        double vy = M.getM()[1][0];
        double vz = M.getM()[2][0];
        return new Vector(vx, vy, vz);
    }

    public static Matrix vectorToMatrix(Vector vector) {
        double[][] m = new double[4][1];
        m[0][0] = vector.xPos();
        m[1][0] = vector.yPos();
        m[2][0] = vector.zPos();
        m[3][0] = 1;
        return new Matrix(m);
    }

    public static Vertex vectorToVertex(Vector v) {
        return new Vertex(v.xPos(), v.yPos(), v.zPos());
    }

    public static Vector vertexToVector(Vertex v) {
        return new Vector(v.xPos(), v.yPos(), v.zPos());
    }

    public static Color get_random_color() {
        Random random = new Random();
        int r, g, b;
        r = g = b = 256;

        // make sure color isn't too light
        while (r + g + b > 128 * 3) {
            r = random.nextInt(256);
            g = random.nextInt(256);
            b = random.nextInt(256);
        }
        return new Color(r, g, b);
    }
}
