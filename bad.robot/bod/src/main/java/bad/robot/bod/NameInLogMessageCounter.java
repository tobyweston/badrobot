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

import static bad.robot.bod.Assertions.assertNotEmpty;
import static bad.robot.bod.Assertions.assertNotNull;

public class NameInLogMessageCounter {

    private final Name name;

    private NameInLogMessageCounter(Name name) {
        assertNotNull(name);
        this.name = name;
    }

    public static NameInLogMessageCounter count(Name name) {
        return new NameInLogMessageCounter(name);
    }

    public Integer in(List<LogEntry> logs) {
        assertNotEmpty(logs);
        int count = 0;
        for (LogEntry log : logs)
            if (log.getMessage().contains(name.value()))
                count++;
        return count;
    }

}
