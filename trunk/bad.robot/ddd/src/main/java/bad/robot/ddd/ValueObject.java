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
public interface ValueObject<T> extends Comparable<T> {
    
    T value();
}
