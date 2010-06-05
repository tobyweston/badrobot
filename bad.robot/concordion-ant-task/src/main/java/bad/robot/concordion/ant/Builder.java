package bad.robot.concordion.ant;

public interface Builder<T, E extends Exception> {
    T build() throws E;
}
