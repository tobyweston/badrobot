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
 * An {@link bad.robot.ddd.Entity} that is externally referenced. For an {@link bad.robot.ddd.Entity} to be classified
 * as an {@link AggregateRoot}, it must conceptually exist <i>wholly</i> on its own. That is to say, within the  business
 * domain of an Auto-Parts Shop, a <code>Tyre</code> might well exist as a whole entity, an aggregate root. Within the business
 * domain of a Car Dealership, the <code>Tyre</code> is more likely to form <i>part of</i> another whole entity; a <code>Car</code>.
 * <p/>
 * The line about being externally referenced therefore is around being wholly identifiable as well as implying the
 * collection of instances being important to the business domain. This in turn implies access via the {@link bad.robot.ddd.Repository}.
 * <p/>
 * It therefore doesn't make sense for an {@link bad.robot.ddd.AggregateRoot} to contain another {@link bad.robot.ddd.AggregateRoot}.
 *
 * @see bad.robot.ddd.Aggregate
 * @see bad.robot.ddd.Repository
 */

public interface AggregateRoot<T extends Identifier> extends Entity<T> {

}