package bad.robot.ddd;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * A member of an {@link bad.robot.ddd.Entity} that is externally referenced.
 *
 * @see bad.robot.ddd.Aggregate
 */

@Documented
@Target(FIELD)
@Retention(RUNTIME)
public @interface AggregateRoot {

}