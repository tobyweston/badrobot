package bad.robot.ddd;

/**
 * <p>
 * A simple object to give <i>meaning</i> or <i>context</i> to some other type it wraps and provide type safety and
 * consistency where ever it is used within th domain. The <i>consistency</i> and <i>context</i> parts are very important
 * as a {@link bad.robot.ddd.ValueObject} should convey additional domain specific meaning to the simple type it wraps.
 * </p>
 * <p>
 * A {@link ValueObject} has no concept of <i>identity</i> and as such, implementations should be equal in terms of
 * {@link Object#equals(Object)} to another {@link ValueObject} with the same {@link #value()}.
 * </p>
 * <p>
 * A {@link ValueObject} should be {@link Immutable}
 * </p>
 * @param <T> the type of value object. For example, a {@link String} {@link bad.robot.ddd.ValueObject} would give
 * context to the use of {@link String} and avoid using general method parameter lists; parameters to methods become
 * type safe and more meaningful. The type should be based on primitive types such as {@link String}, {@link Number} etc
 * (avoid using complex objects).
 */

@Immutable
public interface ValueObject<T> {
    
    T value();
}
