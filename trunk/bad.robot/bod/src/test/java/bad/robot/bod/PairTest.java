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

package bad.robot.bod;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static bad.robot.bod.Name.name;
import static bad.robot.bod.Pair.pair;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PairTest {
    
    private ArrayList<Name> pairs;

    @Test
    public void equality() {
        assertThat(pair(name("bob"), name("cane")), is(equalTo(pair(name("bob"), name("cane")))));
        assertThat(pair(name("bob"), name("cane")), is(equalTo(pair(name("cane"), name("bob")))));
    }

    @Test
    public void shouldAddToSet() {
        Set<Pair> pairs = new HashSet<Pair>();
        pairs.add(pair(name("batman"), name("robin")));
        pairs.add(pair(name("robin"), name("batman")));
        assertThat(pairs.size(), is(1));
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldNotAllowTheSamePair() {
        pair(name("bob"), name("bob"));
    }

    @Test
    public void shouldOrderPair() {
        pairs = pair(name("dog"), name("cat")).getPairs();
        assertThat(pairs.get(0), is(name("cat")));
        assertThat(pairs.get(1), is(name("dog")));
    }

    @Test
    public void shouldImplementToString() {
        assertThat(pair(name("dog"), name("cat")).toString(), is("[cat, dog]"));
    }
}
