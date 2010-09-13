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

import java.util.Arrays;
import java.util.List;

import static bad.robot.bod.Name.name;
import static bad.robot.bod.PairAnalyser.find;
import static java.util.Arrays.asList;

public class PairAnalyserTest {

    private final List<LogEntry> log = asList(new LogEntryBuilder().build());
    private final List<Name> names = asList(name("batman"), name("robin"), name("joker"), name("penguin"));
    private final List<LogEntry> logs = asList(
            new LogEntryBuilder().withMessage("batman, robin - battled enemies, won the day").build(),
            new LogEntryBuilder().withMessage("joke, penguin - plotted").build(),
            new LogEntryBuilder().withMessage("alfred - cleaned the house").build());


    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAttemptToFindOnInvalidName() {
        find(null);
        find(Arrays.<Name>asList());
        find(asList(name("batman")));
    }

    @Test
    public void should() {
        find(names).in(log);
    }

}
