package org.group7.gui.geometric;

/**
 * Generic all-purpose Tuple class. Supports every class type or object.
 * First generic parameter is denoted "A", and second generic parameter is denoted "B"
 * @param <A> First entry in Tuple, can be any class or object
 * @param <B> Second entry in Tuple, Can be any class or object
 */
public class Tuple<A, B> {
    private final A a;
    private final B b;

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
        return (o instanceof Tuple<?, ?>) && ((Tuple<?, ?>) o).getA().equals(a) && ((Tuple<?, ?>) o).getB().equals(b);
    }


//-------------------------------------------------------------------------------------------------------------------------------


    /**
     * EXAMPLE of specialized, parameterized Tuple. We don't need to use it
     * @param <Distance> Any double or subclass of Double
     * @param <Angle> Any Vector2D or subclass of Vector2D
     */
    public static class DistanceAngle<Distance extends Double, Angle extends Vector2D> extends Tuple<Distance, Angle> {
        //Distance and Angle are not classes. They are generic parameters <A,B> renamed for semantic meaning
        public DistanceAngle(Distance d, Angle a) {
            super(d, a); //Distance represents parameter A, Angle represent parameter B
        }
        public Distance getDistance() { return getA(); }    //Semantic meaning given to getA() method (returns a "distance")
        public Angle getAngle() { return getB(); }          //Semantic meaning given to getB() method (returns an "angle")
    }


    //any specific tuple type, where semantic meaning and constraints on the generic parameters are desired,
    //can be represented as a subclass of the Tuple class using generics, as seen above.

}


