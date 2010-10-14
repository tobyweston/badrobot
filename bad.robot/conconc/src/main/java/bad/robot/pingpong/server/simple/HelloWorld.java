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

import com.google.code.tempusfugit.temporal.StopWatch;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;

import java.io.IOException;
import java.io.PrintStream;

import static com.google.code.tempusfugit.temporal.DefaultClock.now;
import static com.google.code.tempusfugit.temporal.Duration.seconds;
import static com.google.code.tempusfugit.temporal.StopWatch.start;
import static com.google.code.tempusfugit.temporal.Timeout.timeout;
import static com.google.code.tempusfugit.temporal.WaitFor.waitUntil;

public class HelloWorld {

    public void hello(Request request, Response response) {
        PrintStream body = null;
        try {
            body = response.getPrintStream();
            StopWatch watch = start(now());
            response.set("Content-Type", "text/plain");
            response.set("Server", "HelloWorld/1.0 (Simple 4.0)");
            response.setDate("Date", watch.markAndGetTotalElapsedTime().inMillis());
            waitUntil(timeout(seconds(10)));
            response.setDate("Last-Modified", watch.markAndGetTotalElapsedTime().inMillis());
            body.printf("Hello World (took %dms)%n", response.getDate("Last-Modified"));
            System.out.printf("Processed on %s, after %s%n", Thread.currentThread().getName(), watch.markAndGetTotalElapsedTime());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (body != null)
                body.close();
        }
    }
}