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

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import static bad.robot.bod.Name.name;
import static bad.robot.bod.Pair.pair;
import static bad.robot.bod.PairFinder.find;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PairFinderTest {

    private static final Pair AB = pair(name("A"), name("B"));
    private static final Pair AC = pair(name("A"), name("C"));
    private static final Pair AD = pair(name("A"), name("D"));
    private static final Pair BC = pair(name("B"), name("C"));
    private static final Pair BD = pair(name("B"), name("D"));
    private static final Pair CD = pair(name("C"), name("D"));
                                       
    @Test
    public void shouldFindPairs() {
        Set<Pair> pairs = find().pairs(asList(name("A"), name("B"), name("C"), name("D")));
        assertThat(pairs, is(set(AB, AC, AD, BC, BD, CD)));
    }
    
    @Test
    public void shouldFindPairsIgnoringDuplicates() {
        Set<Pair> pairs = find().pairs(asList(name("A"), name("B"), name("C"), name("B"), name("D")));
        assertThat(pairs, is(set(AB, AC, AD, BC, BD, CD)));
    }

    private <T> Set<T> set(T... items) {
        SortedSet<T> set = new TreeSet<T>();
        for (T item : items)
            set.add(item);
        return set;
    }

}
