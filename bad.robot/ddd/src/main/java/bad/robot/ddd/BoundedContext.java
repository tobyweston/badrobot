package bad.robot.ddd;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * The applicability of a given model, fairly open ended at this point but I imagine it will firm up into describing
 * contexts against <i>specific</i> classes and interfaces. For example, only being appropriate for use with
 * {@link bad.robot.ddd.Entity}.
 */

@Documented
@Target({TYPE, METHOD})
@Retention(RUNTIME)
public @interface BoundedContext {

    String value() default "";

}