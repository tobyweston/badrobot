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

import bad.robot.pingpong.UncheckedException;
import bad.robot.pingpong.server.StandardPing;
import com.google.code.tempusfugit.temporal.StopWatch;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;

import java.io.IOException;
import java.io.PrintStream;

import static bad.robot.pingpong.server.simple.StandardResponseHeader.standardResponseHeaders;
import static bad.robot.pingpong.transport.ResponseCode.OK;
import static com.google.code.tempusfugit.temporal.RealClock.now;

class Ping implements Container {

    @Override
    public void handle(Request request, Response response) {
        PrintStream body = null;
        try {
            StopWatch watch = StopWatch.start(now());
            standardResponseHeaders().setOn(response, OK);
            body = response.getPrintStream();
            body.print(new StandardPing().ping());
            System.out.printf("Processed on %s, after %s%n", Thread.currentThread().getName(), watch.markAndGetTotalElapsedTime());
        }
        catch (IOException e) {
            throw new UncheckedException(e);
        } finally {
            if (body != null)
                body.close();
        }
    }

}