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
 * An <i>identifiable</i> domain object, an object that exists <i>because</i> it has an {@link bad.robot.ddd.Identifier}
 * and without the idea of being uniquely identifiable, the object has less gravitas and meaning.
 *
 * @param <T> the type of the entities identifier, a special type of {@link bad.robot.ddd.ValueObject}
 * @see bad.robot.ddd.Identifier
 * @see bad.robot.ddd.Identifiable
 */
public interface Entity<T extends Identifier> extends Identifiable<T> {
    
}
