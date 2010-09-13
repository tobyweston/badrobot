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

import org.apache.commons.lang.builder.CompareToBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static bad.robot.bod.Assertions.assertNotNullAndDifferent;
import static java.util.Collections.sort;
import static org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang.builder.HashCodeBuilder.reflectionHashCode;

public class Pair implements Comparable<Pair> {

    private final List<Name> names = new ArrayList<Name>();

    public static Pair pair(Name first, Name second) {
        return new Pair(first, second);
    }

    private Pair(Name first, Name second) {
        assertNotNullAndDifferent(first, second);
        names.add(first);
        names.add(second);
        sort(names, new Comparator<Name>() {
            public int compare(Name first, Name second) {
                return first.compareTo(second.value());
            }
        });
    }

    public ArrayList<Name> getPairs() {
        return new ArrayList(names);
    }

    @Override
    public boolean equals(Object o) {
        return reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }

    public int compareTo(Pair other) {
        return new CompareToBuilder().append(names.get(0), other.names.get(0).value())
                .append(names.get(1), other.names.get(1).value()).toComparison();
    }

    @Override
    public String toString() {
        return Arrays.toString(getPairs().toArray());
    }
}
