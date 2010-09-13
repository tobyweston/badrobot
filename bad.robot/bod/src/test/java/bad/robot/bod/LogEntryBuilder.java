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

import java.util.Date;

public class LogEntryBuilder {

    private String revision = "234";
    private  String username = "bob";
    private Date date = new Date();
    private  Integer lines = 1;
    private  String message = "some general message";

    public LogEntryBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public LogEntry build() {
        return new LogEntry(revision, username, date, lines, message);
    }
}
