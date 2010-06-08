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

import static bad.robot.ddd.AbstractEntityTest.StubValueType.stubValueType;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AbstractEntityTest {

    private final Identifier<String> identifier = new Identifier<String>("id") { };


    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAllowNullIdentifier() {
        new StubAbstractEntity(null, stubValueType("foo"));
    }

    @Test
    public void shouldAllowValidIdentifier() {
        StubIdentifier expectedId = new StubIdentifier("abc");
        Identifier actualId = new StubAbstractEntity(expectedId, stubValueType("foo")).getIdentifier();
        assertEquals(expectedId, actualId);
    }

    @Test
    public void equalityIsBasedOnIdentityOnly() {
        assertThat(new StubAbstractEntity(identifier, stubValueType("foo")), is(equalTo(new StubAbstractEntity(identifier, stubValueType("bar")))));
    }

    private static class StubIdentifier extends Identifier<String> {
        public StubIdentifier(String value) {
            super(value);
        }
    }

    private static class StubAbstractEntity extends AbstractEntity<Identifier> {
        private ValueObject<String> field; // not used in equality

        public StubAbstractEntity(Identifier<String> id, ValueObject<String> field) {
            super(id);
            this.field = field;
        }
    }

    static class StubValueType extends AbstractValueObject<String> {
        public static StubValueType stubValueType(String value) {
            return new StubValueType(value);
        }

        private StubValueType(String value) {
            super(value);
        }
    }
}

