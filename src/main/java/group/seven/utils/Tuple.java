package group.seven.utils;

public record Tuple<A, B>(A a, B b) {
    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + a + ", " + b + ')';
    }
}
