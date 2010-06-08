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

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import static bad.robot.ddd.Assertions.assertNotNull;
import static org.apache.commons.lang.builder.ToStringStyle.SHORT_PREFIX_STYLE;

/**
 * Default implementation of @{link ValueObject}, provides a <i>fungible</i> value type.
 *
 * @param <T>
 * @see bad.robot.ddd.ValueObject
 */

@Immutable
public abstract class AbstractValueObject<T> implements ValueObject<T> {
    private final T value;

    public AbstractValueObject(T value) {
        assertNotNull(value);
        this.value = value;
    }

    @Override
    public final T value() {
        return value;
    }

    @Override
    public int compareTo(T other) {
        return new CompareToBuilder().append(value, other).toComparison();
    }

    @Override
    public final int hashCode() {
        return new HashCodeBuilder().append(value).toHashCode();
    }

    @Override
    public final boolean equals(Object other) {
        if (this.getClass().equals(other.getClass()))
            return new EqualsBuilder().append(value, ((AbstractValueObject) other).value).isEquals();
        return false;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE).append(value).toString();
    }
}
