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
 *
 * @see {@link BoundedContext}
 * @see {@link bad.robot.ddd.AggregateRoot}
 * @see {@link bad.robot.ddd.Entity}
 * @see {@link bad.robot.ddd.ValueObject}
 */
public @interface Aggregate {

    // members should be Entity or ValueObjects.


}
