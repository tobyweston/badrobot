/*
 * Copyright (c) 2009-2011, bad robot (london) ltd
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

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;

import java.util.concurrent.Executor;

class AsynchronousContainer implements Container {

    private final Executor scheduler;
    private final Container delegate;

    public AsynchronousContainer(Executor scheduler, Container delegate) {
        this.scheduler = scheduler;
        this.delegate = delegate;
    }

    public void handle(final Request request, final Response response) {
        System.out.printf("Request Handler thread %s%n", Thread.currentThread().getName());
        scheduler.execute(new Runnable() {
            public void run() {
                delegate.handle(request, response);
            }
        });
    }
}
