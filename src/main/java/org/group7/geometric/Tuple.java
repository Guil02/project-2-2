package org.group7.geometric;

import javafx.geometry.Point2D;

/**
 * Generic all-purpose Tuple class. Supports every class type or object.
 * First generic parameter is denoted "A", and second generic parameter is denoted "B"
 * Note: A, and B are not classes, their names were chosen arbitrary. Could have called them < X, Y>, < Double, Jesus>, < Vector2D, Penis>, < First, Second>, etc; anything.
 * They are generic types and are therefore representative of any class type. Their names do not represent a specific class.
 *  * Convention usually denote generic types as T or E, when there's just one parameter. Maybe T1,T2 make more sense as parameter names? idk
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
        return (o instanceof Tuple<?, ?>) && ((Tuple<?, ?>) o).getA().equals(getA()) && ((Tuple<?, ?>) o).getB().equals(getB());
    }


//-------------------------------------------------------------------------------------------------------------------------------


    /**
     * This class is a Tuple which is given the semantic meaning of representing a (Distance, Angle) Tuple.
     * Here the "A" parameter has been renamed to "Distance", and the "B" parameter has been renamed to "Angle"
     * The generic parameter Distance can only accept classes that are or extend from Double
     * The generic parameter Angle, can only accept classes which are or extend from Vector2D
     *
     * This class is pretty much redundant because the Tuple class already can represent any Tuple, including this one.
     * However, the parameter constraints and the renaming of the parameters gives this class more semantic meaning
     * and would be easier to understand while reading/writing code.
     * For example tuple.getDistance() is a lot easier to understand than tuple.getA(), when reading code. Since it may not be obvious what "A" represents for that tuple
     * Also, the constraints ensure that tuples of this type must actually be representative of (Distance, Angle) tuples.
     *
     * But at this point using generic types/parameterized classes is pretty pointless; I can't think of many scenarios
     * where a non-parameterized class is not more preferable
     *
     * Constraining the parameter types isn't strictly necessary, but otherwise you can pass e.g. a Menu object as the Distance parameter,
     * which does not really make any sense in this context; however, you may want Integers, Longs, Vectors, etc to also be able to represent distances
     * In that case constraining the parameter type is undesirable. Same with angle, perhaps Vector2D is not the only way to represent
     * an angle and so removing the "extends Vector2D" constraint may be desirable as well.
     *
     * In the base Tuple class the parameters don't have any semantic meaning, so passing a Menu object as parameter "A"
     * could be a perfectly reasonable thing to do depending on the context and purpose of that Tuple.
     *
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


//----------------------------------------------------------------------------------------------------------------------------


    /*
    This is just some demo code to help with the intuition for understanding how and java generics work in the context
    of Tuples and their subclasses.

    There is a trade-off between semantic intuition and flexibility. Tuples are extremely
    flexible since there are no constraints on the class types the generic parameters A,B can represent. However, without
    proper variable names it may be hard to infer what objects/classes the getA() and getB() method will return.

    Subclasses of the Tuple class with parameter renaming and constraining, can give much more semantic meaning to what
    any given tuple actually represents, and ensures that arbitrary tuples of that subclass type aren't created. However,
    it can be annoying to have to create a new tuple subclass every time you want to use a specific kind tuple.
    In that case using the Tuple superclass is preferred bc it can represent any tuple of any kind; however descriptive
    variable names should be used to give semantic meaning to the getA() and getB() methods.

     */
    //TODO: Delete this:
    public static void main(String[] args) {
        double distance = 10;
        Vector2D angle = new Vector2D(90);

        //Proper variable names give semantic meaning to the parameters A, B; when using the Tuple Class
        Tuple<Double, Vector2D> distanceAngleTuple = new Tuple<>(distance, angle);
        System.out.println(distanceAngleTuple);
        //We can infer that getA() will return a double representing the distance, and getB() a Vector2D representing angle
        System.out.println("(A = "+distanceAngleTuple.getA() + ", B = " + distanceAngleTuple.getB() + ")\n\n");


        //Using the DistanceAngle subclass we know immediately this tuple represents distances and angles. Variable name not
        //as important for code readability
        DistanceAngle<Double, Vector2D> distanceAngle = new DistanceAngle<>(distance, angle);
        System.out.println(distanceAngle);
        //The methods getDistance(), and getAngle(), give clear semantic meaning to the getA() and getB() methods they invoke
        System.out.println("(Distance/A =  "+ distanceAngle.getDistance() + ", Angle/B =  " + distanceAngle.getAngle() + ")\n\n");


        //both representations of the tuple are equal if they contain the same values for their two parameters
        System.out.println(distanceAngleTuple.equals(distanceAngle));

        /*
        The additional constraints placed on the DistanceAngle Tuple subclass ensures the DistanceAngle tuple cannot represent
        arbitrary tuples like the base Tuple class can.

        The following line of code will throw a compilation error, because String does not extend from Vector2D: */

        // DistanceAngle<Double, String> invalidDistAngleTuple = new DistanceAngle<>(distance, "strings are not angles!");


        //this is fine tho: the Tuple super-class has no constraints on its parameters.
        //Any class/object can represent parameters A and B
        Tuple<Double, String> justATuple = new Tuple<>(distance, "ladila");
        System.out.println(justATuple);

        Tuple<String, Point2D> thisIsFineToo = new Tuple<>("random string!", new Point2D(0, 1));
        System.out.println(thisIsFineToo);
    }


}


