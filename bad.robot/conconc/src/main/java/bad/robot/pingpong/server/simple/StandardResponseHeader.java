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

package bad.robot.pingpong.server.simple;

import bad.robot.pingpong.transport.ResponseCode;
import org.simpleframework.http.Response;

import java.util.Date;

import static com.google.code.tempusfugit.temporal.DefaultClock.now;

public class StandardResponseHeader implements ResponseHeaderSetter {

    private StandardResponseHeader() {
    }

    public static StandardResponseHeader standardResponseHeaders() {
        return new StandardResponseHeader();
    }

    @Override
    public void setOn(Response response, ResponseCode code) {
        Date now = now().create();
        response.setCode(code.getCode());
        response.set("Content-Type", "text/plain");
        response.set("Server", "PingPong/1.0");
        response.setDate("Date", now.getTime());
        response.setDate("Last-Modified", now.getTime());
    }

}
