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

import java.util.List;

import static bad.robot.bod.Name.name;
import static bad.robot.bod.NameInLogMessageCounter.count;
import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class NameInLogMessageCounterTest {

    private final List<LogEntry> logs = asList(
            new LogEntryBuilder().withMessage("fred was here").build(),
            new LogEntryBuilder().withMessage("as well as fred, so waz billy").build(),
            new LogEntryBuilder().withMessage("but fred didn't see fred").build());

    @Test (expected = IllegalArgumentException.class)
    public void shouldNotAllowNull() {
        count(null);
        count(name("bob")).in(null);
    }

    @Test
    public void shouldCountNames() {
        assertThat(count(name("billy")).in(logs), is(1));
        assertThat(count(name("fred")).in(logs), is(3));
    }
}
