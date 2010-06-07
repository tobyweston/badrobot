package bad.robot.ddd;

/**
 * Used within the context of an {@link bad.robot.ddd.Entity}, the unique identifier value object.
 *
 * @param <T> the type of identifier
 * @see bad.robot.ddd.ValueObject
 */
public interface Identifier<T> extends ValueObject<T> {
}
