package bad.robot.ddd;

/**
 * A cluster of associated objects ({@link bad.robot.ddd.Entity} or {@link bad.robot.ddd.ValueObject}) that are treated
 * as a unit for the purpose of model changes. Only a single member should be referenced externally, this is
 * the {@link bad.robot.ddd.AggregateRoot}.
 *
 * @see {@link BoundedContext}
 * @see {@link bad.robot.ddd.AggregateRoot}
 * @see {@link bad.robot.ddd.Entity}
 * @see {@link bad.robot.ddd.ValueObject}
 */
public @interface Aggregate {

    // members should be Entity or ValueObjects.


}
