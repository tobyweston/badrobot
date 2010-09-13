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

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static bad.robot.bod.ListRemoval.list;
import static bad.robot.bod.Pair.pair;

public class PairFinder {

    private PairFinder() {
    }

    public static PairFinder find() {
        return new PairFinder();
    }

    public Set<Pair> pairs(List<Name> names) {
        Set<Pair> pairs = new TreeSet<Pair>();
        for (Name head : names) {
            for (Name tail : list(names).minus(head)) {
                pairs.add(pair(head, tail));
            }
        }
        return pairs;
    }

}
