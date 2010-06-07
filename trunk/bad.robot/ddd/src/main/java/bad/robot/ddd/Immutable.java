package bad.robot.ddd;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * Indicates that a class is intended to be immutable. Look it up!
 */

@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface Immutable {
}
