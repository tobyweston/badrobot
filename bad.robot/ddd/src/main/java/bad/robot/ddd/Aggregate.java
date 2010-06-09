/*
 * Copyright (c) 2009-2010, bad robot (london) ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bad.robot.ddd;

/**
 * A cluster of associated objects ({@link bad.robot.ddd.Entity} or {@link bad.robot.ddd.ValueObject}) that are treated
 * as a unit for the purpose of model changes. Only a single member should be referenced externally, this is
 * the {@link bad.robot.ddd.AggregateRoot}.
 * <p/>
 * Because an {@link bad.robot.ddd.Aggregate} captures some domain noun, it should be made up of other well formed
 * domain constructs. It should therefore only contain members which are of the following types.
 * <ul>
 * <li>{@link bad.robot.ddd.ValueObject}</li>
 * <li>{@link Entity}</li>
 * <li>{@link Aggregate}</li>
 * </ul>
 * If it were to be made up of for example, vanilla objects, the <i>meaning</i> of that object within the business context
 * would be lost and the value of the {@link bad.robot.ddd.Aggregate} is reduced. How can the team have a clear understanding
 * of this {@link bad.robot.ddd.Aggregate} if it is composed of less well understood composites.
 *
 * @see bad.robot.ddd.BoundedContext
 * @see bad.robot.ddd.AggregateRoot
 * @see bad.robot.ddd.Entity
 * @see bad.robot.ddd.ValueObject
 */
public @interface Aggregate {

    // not clear about the relationship between aggregate and entity. is it a "is a"? maybe not as it can still be a valid
    // domain aggregate without an id?

}
