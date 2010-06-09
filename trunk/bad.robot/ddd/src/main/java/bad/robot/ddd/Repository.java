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
 * A {@link Repository} represents the collection of {@link bad.robot.ddd.AggregateRoot}s within the business domain.
 * For example, within the domain of a Telesales Agency, the collection of targeted sales might come from a 
 * <code>Phonebook</code>.
 *
 * @param <T> the {@link AggregateRoot} the repository contains
 * @param <I> the {@link bad.robot.ddd.Identifier} of the {@link bad.robot.ddd.AggregateRoot}
 */
public interface Repository<T extends AggregateRoot<I>, I extends Identifier> {


}
