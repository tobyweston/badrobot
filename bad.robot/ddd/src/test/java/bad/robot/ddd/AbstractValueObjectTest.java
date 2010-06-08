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

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class AbstractValueObjectTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAllowNullValues() {
        new AbstractValueObject<String>(null) {
        };
    }

    @Test
    public void shouldDoBasicEquality() {
        assertThat(new SuperAbstractValueType("eric"), is(equalTo(new SuperAbstractValueType("eric"))));
    }

    @Test
    public void shouldNotAllowEqualityBetweenSubClassAndItsSuperClass() {
        assertThat(new SuperAbstractValueType("eric"), is(not(equalTo((SuperAbstractValueType) new SubClassedAbstractValueType("eric")))));
    }

    private static class SuperAbstractValueType extends AbstractValueObject<String> {
        public SuperAbstractValueType(String value) {
            super(value);
        }
    }

    private static class SubClassedAbstractValueType extends SuperAbstractValueType {
        public SubClassedAbstractValueType(String value) {
            super(value);
        }
    }

}
