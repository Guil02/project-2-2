package group.seven.utils;

/**
 * A matrix class to do matrix operations
 */
public class Matrix {
    public final int rows;
    public final int columns;
    private double[][] data;

    /**
     * @param rows    the amount of rows in the matrix
     * @param columns the amount of columns in the matrix
     */
    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        data = new double[rows][columns];

        // randomly initialize the data
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                data[i][j] = Math.random();
            }
        }
    }

    /**
     * @param data the data already predefined in an array
     */
    public Matrix(double[][] data) {
        this.data = data;
        rows = data.length;
        columns = data[0].length;
    }

    /**
     * creates matrix from a provided array with dimension of the given row and column
     *
     * @param row amount of rows
     * @param col amount of columns
     * @param arr the data array
     * @return
     */
    public static Matrix fromArray(int row, int col, double[] arr) {
        if (row * col != arr.length) {
            System.err.println("the rows and columns provided did not equal to the length of the array");
            return null;
        }
        double[][] data = new double[row][col];
        int index = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                data[i][j] = arr[index++];
            }
        }

        return new Matrix(data);
    }

    /**
     * method to set a specific value in the matrix
     *
     * @param x   row in which {@code val} should be added
     * @param y   column in which {@code val} should be added
     * @param val value that should be added.
     */
    public void set(int x, int y, double val) {
        try {
            data[x][y] = val;
        } catch (IndexOutOfBoundsException e) {
            System.err.println("indices used where not valid indices, value: " + val + " could not be placed at indices x= " + x + ", y= " + y);
        }
    }

    /**
     * a method that returns a matrix containing the addition between this matrix and another.
     *
     * @param otherData the data that should be added to {@link Matrix#data}
     * @return the new matrix resulting from the addition
     */
    public Matrix add(double[][] otherData) {
        if (rows != otherData.length || columns != otherData[0].length) {
            System.err.println("cannot add matrices due to shape mismatch");
            return null;
        }

        double[][] newData = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                newData[i][j] = data[i][j] + otherData[i][j];
            }
        }

        return new Matrix(newData);
    }

    /**
     * works the same as {@link Matrix#add(double[][])} but has a matrix as parameter instead of a double array
     *
     * @param m the matrix that should be added to this matrix
     * @return the new matrix resulting from the addition
     */
    public Matrix add(Matrix m) {
        return add(m.getData());
    }

    /**
     * a method that returns a matrix containing the subtraction between this matrix and another.
     *
     * @param otherData the data that should be subtracted from {@link Matrix#data}
     * @return the new matrix resulting from the subtraction
     */
    public Matrix subtract(double[][] otherData) {
        if (rows != otherData.length || columns != otherData[0].length) {
            System.err.println("cannot add matrices due to shape mismatch");
            return null;
        }

        double[][] newData = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                newData[i][j] = data[i][j] - otherData[i][j];
            }
        }

        return new Matrix(newData);
    }

    /**
     * works the same as {@link Matrix#subtract(double[][])} but has a matrix as parameter instead of a double array
     *
     * @param m the matrix that should be subtracted from this matrix
     * @return the new matrix resulting from the subtraction
     */
    public Matrix subtract(Matrix m) {
        return subtract(m.getData());
    }

    /**
     * transposes this matrix
     *
     * @return the transposed matrix
     */
    public Matrix transpose() {
        double[][] newData = new double[columns][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                newData[j][i] = data[i][j];
            }
        }
        return new Matrix(newData);
    }

    /**
     * does the dot product between this matrix and another
     *
     * @param m the matrix that with which this matrix needs to be multiplied (dot product)
     * @return the resulting matrix of the dot product operation.
     */
    public Matrix dot(Matrix m) {
        if (columns != m.rows) {
            System.err.println("shape mismatch, Matrix a column size (" + columns + ") not equal to matrix b row size (" + m.rows + ") ");
            return null;
        }

        double[][] newData = new double[rows][m.columns];

        for (int i = 0; i < newData.length; i++) {
            for (int j = 0; j < newData[i].length; j++) {
                double sum = 0;
                for (int k = 0; k < columns; k++) {
                    sum += data[i][k] * m.getData()[k][j];
                }
                newData[i][j] = sum;
            }
        }
        return new Matrix(newData);
    }

    /**
     * does an element wise multiplication between this matrix and another
     *
     * @param m the other matrix.
     * @return the resulting matrix of this operation
     */
    public Matrix multiply(Matrix m) {
        double[][] newData = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                newData[i][j] = this.data[i][j] * m.getData()[i][j];
            }
        }
        return new Matrix(newData);
    }

    /**
     * multiplies the matrix with a scalar.
     *
     * @param scalar the scalar value
     * @return the resulting matrix of this operation
     */
    public Matrix multiply(double scalar) {
        double[][] newData = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                newData[i][j] = this.data[i][j] * scalar;
            }
        }
        return new Matrix(newData);
    }

    /**
     * getter for the data
     *
     * @return the data stored in this matrix
     */
    public double[][] getData() {
        return data;
    }

    /**
     * Overrides the current data with new data.
     *
     * @param data the new data
     */
    public void setData(double[][] data) {
        this.data = data;
    }

    /**
     * transform the matrix into 1 long matrix.
     *
     * @return the matrix resulting from this operation
     */
    public double[] toArray() {
        double[] array = new double[rows * columns];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                array[index++] = data[i][j];
            }
        }
        return array;
    }

    /**
     * This method will run the matrix through a sigmoid
     * https://en.wikipedia.org/wiki/Sigmoid_function
     *
     * @return a matrix containing the result of the operation
     */
    public Matrix sigmoid() {
        double[][] newData = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                newData[i][j] = 1 / (1 + Math.exp(data[i][j]));
            }
        }

        return new Matrix(newData);
    }

    @Override
    public String toString() {
        return "Matrix{" +
                "rows=" + rows +
                ", columns=" + columns +
                '}';
    }
}
