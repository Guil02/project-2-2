package org.group7.alt.logic.util;

/**
 * Generic all-purpose Tuple class. Supports every class type or object.

 * @param <A> First entry in Tuple, can be any class or object
 * @param <B> Second entry in Tuple, Can be any class or object
 */
public class Tuple<A, B> {
    private A a;
    private B b;

    public Tuple() {}

    public Tuple(A a, B b) {
        this.a = a;
        this.b = b;
    }

    /**
     * Retrieves the object associated to this Tuple's "A" parameter
     * @return the object associated with the parameter "A"
     */
    public A getA() {
        return a;
    }

    /**
     * Retrieves object associated with second Tuple parameter "B"
     * @return the object passed as parameter "B"
     */
    public B getB() {
        return b;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + a + ", " + b + ')';
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Tuple<?, ?>) && ((Tuple<?, ?>) o).getA().equals(getA()) && ((Tuple<?, ?>) o).getB().equals(getB());
    }


//-------------------------------------------------------------------------------------------------------------------------------


//    /**
//     *
//     * This class is a Tuple which is given the semantic meaning of representing a (Distance, Angle) Tuple.
//     * Here the "A" parameter has been renamed to "Distance", and the "B" parameter has been renamed to "Angle"
//     * The generic parameter Distance can only accept classes that are or extend from Double
//     * The generic parameter Angle, can only accept classes which are or extend from Vector2D
//
//     * @param <Distance> Any double or subclass of Double
//     * @param <Angle> Any Vector2D or subclass of Vector2D
//     */
//    public static class DistanceAngle<Distance extends Double, Angle extends Vector2D> extends Tuple<Distance, Angle> {
//        //Distance and Angle are not classes. They are generic parameters <A,B> renamed for semantic meaning
//        public DistanceAngle(Distance d, Angle a) {
//            super(d, a); //Distance represents parameter A, Angle represent parameter B
//        }
//        public Distance getDistance() { return getA(); }    //Semantic meaning given to getA() method (returns a "distance")
//        public Angle getAngle() { return getB(); }          //Semantic meaning given to getB() method (returns an "angle")
//    }
//
}


