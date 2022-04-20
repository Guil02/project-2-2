package org.group7.gui.geometric;


//inspired by: https://gist.github.com/gunvirranu/6816d65c0231981787ebefd3bdb61f98
public class Vector2D {

    public double x;
    public double y;


    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Vector2D(double angle){
        this.x = Math.cos(angle);
        this.y = Math.sin(angle);

    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vector2D v) {
        this.x = v.x;
        this.y = v.y;
    }

    public double getLength() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector2D getNormalized() {
        double magnitude = getLength();
        return new Vector2D(x / magnitude, y / magnitude);
    }

    public Vector2D add(Vector2D v) {
        return new Vector2D(this.x + v.x, this.y + v.y);
    }

    public Vector2D sub(Vector2D v) {
        return new Vector2D(this.x - v.x, this.y - v.y);
    }

    public double dot(Vector2D v) {
        return (this.x * v.x + this.y * v.y);
    } //dot product

    public Vector2D scale(double scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

    public double distance(Vector2D v) { // can we re name it to euclideanDistance?
        double vx = v.x - this.x;
        double vy = v.y - this.y;
        return Math.sqrt(vx * vx + vy * vy);
    }

    public double getAngle() {
        return Math.atan2(y, x);
    }

    //angle = radian
    public Vector2D getRotatedBy(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        return new Vector2D(x * cos - y * sin, x * sin + y * cos);
    }

    @Override
    public Vector2D clone() {
        return new Vector2D(x, y);
    }

    @Override
    public String toString() {
        return "Vector2d[" + x + ", " + y + "]";
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }
        if (obj instanceof Vector2D) {
            Vector2D v = (Vector2D) obj;
            boolean a = (x-v.x)<0.000001 && (x-v.x)>-0.00001;
            boolean b = (y-v.y)<0.000001 && (y-v.y)>-0.00001;

            return a && b;
            //return (Math.abs(x - v.x) < 0.000001) && (Math.abs(y - v.y) < 0.000001); (?) wouldn't this one-liner work?
        }
        return false;
    }
}
