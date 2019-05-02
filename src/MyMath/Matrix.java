package MyMath;

public class Matrix {
    private double[][] M;

    public Matrix(double[][] m) {
        this.M = m;
    }

    public double[][] getM() {
        return this.M;
    }

    public Matrix mult(Matrix other) {
        return mult(this, other);
    }

    public static double[][] mult(double[][] A, double[][] B) {
        int aRows = A.length;
        int aColumns = A[0].length;
        int bRows = B.length;
        int bColumns = B[0].length;

        if (aColumns != bRows) {
            throw new IllegalArgumentException("no match cols and rows");
        }

        // result mat
        double[][] C = new double[aRows][bColumns];

        // pass over A (and C) rows
        for (int i = 0; i < aRows; i++) {
            // pass over b (and C) cols
            for (int j = 0; j < bColumns; j++) {
                // pass over a cols
                for (int k = 0; k < aColumns; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }

    public static Matrix mult(Matrix A, Matrix B) {
        return new Matrix(mult(A.getM(), B.getM()));
    }

    public Matrix transpose() {
        int rows = this.getRows();
        int cols = this.getCols();
        double[][] N = new double[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                N[j][i] = this.M[i][j];
            }
        }
        return new Matrix(N);
    }

    @Override
    public String toString() {
        int rows = this.getRows();
        int cols = this.getCols();

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                output.append(this.M[i][j]).append("\t");
            }
            output.append("\n");
        }
        return output.toString();
    }

    int getRows() {
        return this.M.length;
    }

    int getCols() {
        return this.M[0].length;
    }
}