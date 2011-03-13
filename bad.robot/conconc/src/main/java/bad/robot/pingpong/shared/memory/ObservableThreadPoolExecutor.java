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

package bad.robot.pingpong.shared.memory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

class ObservableThreadPoolExecutor extends ThreadPoolExecutor  {

    private final ThreadPoolObserver observer;

    public ObservableThreadPoolExecutor(int threads, ThreadFactory factory, ThreadPoolObserver observer) {
        super(threads, threads, 0L, MILLISECONDS, new LinkedBlockingQueue<Runnable>(), factory);
        this.observer = observer;
    }

    @Override
    protected void beforeExecute(Thread thread, Runnable task) {
        observer.beforeExecute(thread, task);
    }

    @Override
    protected void afterExecute(Runnable task, Throwable throwable) {
        observer.afterExecute(task, throwable);
    }

    @Override
    protected void terminated() {
        observer.terminated();
    }
}
