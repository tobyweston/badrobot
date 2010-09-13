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

import static bad.robot.bod.Assertions.assertNotNull;
import static bad.robot.bod.NameInLogMessageCounter.count;

public class PairAnalyser {

    private final List<Name> names;

    private PairAnalyser(List<Name> names) {
        this.names = names;
    }

    public static PairAnalyser find(List<Name> names) {
        if (names == null || names.size() < 2)
            throw new IllegalArgumentException("can only analyse two or more names");
        return new PairAnalyser(names);
    }

    public void in(List<LogEntry> logs) {
        assertNotNull(logs);
        for (Name name : names) {
            count(name).in(logs);
        }
    }

}
