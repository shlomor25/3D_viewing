package MyMath;

public class Vector {
    private double xPos;
    private double yPos;
    private double zPos;

    public Vector(double xPos, double yPos, double zPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
    }

    public Vector(String xPos, String yPos, String zPos) {
        this.xPos = Double.parseDouble(xPos);
        this.yPos = Double.parseDouble(yPos);
        this.zPos = Double.parseDouble(zPos);
    }

    public Vector add(Vector v) {
        return add(this, v);
    }

    public Vector sub(Vector v) {
        return sub(this, v);
    }

    public Vector scalarMult(double a) {
        return scalarMult(a, this);
    }

    public double dotProduct(Vector v) {
        return dotProduct(this, v);
    }

    public Vector crossProduct(Vector v) {
        return crossProduct(this, v);
    }

    public static Vector add(Vector v1, Vector v2) {
        double xAdd = v1.xPos() + v2.xPos();
        double yAdd = v1.yPos() + v2.yPos();
        double zAdd = v1.zPos() + v2.zPos();
        return new Vector(xAdd, yAdd, zAdd);
    }

    public static Vector sub(Vector v1, Vector v2) {
        double xAdd = v1.xPos() - v2.xPos();
        double yAdd = v1.yPos() - v2.yPos();
        double zAdd = v1.zPos() - v2.zPos();
        return new Vector(xAdd, yAdd, zAdd);
    }

    public static Vector scalarMult(double a, Vector v) {
        double xRes = a * v.xPos();
        double yRes = a * v.yPos();
        double zRes = a * v.zPos();
        return new Vector(xRes, yRes, zRes);
    }

    public static double dotProduct(Vector v1, Vector v2) {
        double xRes = v1.xPos() * v2.xPos();
        double yRes = v1.yPos() * v2.yPos();
        double zRes = v1.zPos() * v2.zPos();
        return xRes + yRes + zRes;
    }

    public static Vector crossProduct(Vector v1, Vector v2) {
        double xRes = v1.yPos() * v2.zPos() - v1.zPos() * v2.yPos();
        double yRes = v1.zPos() * v2.xPos() - v1.xPos() * v2.zPos();
        double zRes = v1.xPos() * v2.yPos() - v1.yPos() * v2.xPos();
        return new Vector(xRes, yRes, zRes);
    }

    public static double angle(Vector v1, Vector v2) {
        double cAngle = dotProduct(v1, v2) / (v1.magnitude() * v2.magnitude());
        return Math.acos(cAngle);
    }

    public double magnitude() {
        double xMul = this.xPos * this.xPos;
        double yMul = this.yPos * this.yPos;
        double zMul = this.zPos * this.zPos;
        return Math.sqrt(xMul + yMul + zMul);
    }

    public Vector normalize() {
        return this.scalarMult(1 / this.magnitude());
    }

    public boolean isAffineCombination() {
        return (this.xPos + this.yPos + this.zPos == 1);
    }

    public boolean isConvexCombination() {
        boolean notNeg = (xPos >= 0) && (yPos >= 0) && (zPos >= 0);
        return notNeg && this.isAffineCombination();
    }

    public double xPos() {
        return this.xPos;
    }

    public double yPos() {
        return this.yPos;
    }

    public double zPos() {
        return this.zPos;
    }

    public void setzPos(double zPos) {
        this.zPos = zPos;
    }

    public void setxPos(double xPos) {
        this.xPos = xPos;
    }

    public void setyPos(double yPos) {
        this.yPos = yPos;
    }

    public String toString() {
        return "(" + this.xPos + "," + this.yPos + "," + this.zPos + ")";
    }
}
