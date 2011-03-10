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

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ObservableThreadFactory implements ThreadFactory {

    private final static AtomicInteger count = new AtomicInteger(0);

    private final ThreadObserver observer;

    public ObservableThreadFactory(ThreadObserver observer) {
        this.observer = observer;
    }

    @Override
    public Thread newThread(final Runnable runnable) {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    observer.threadStarted();
                    runnable.run();
                } finally {
                    observer.threadTerminated();
                }
            }
        }, this.getClass().getPackage().getName() + "Thread-" + count.incrementAndGet());
        observer.threadCreated();
        return thread;
    }

//    public static class ObservableThread extends Thread {
//
//        public ObservableThread() {
//            super();
//            observer.threadNew();
//        }
//
//        @Override
//        public void run() {
//            observer.threadRunnable();
//            try {
//                super.run();
//            } finally {
//                observer.threadTerminated();
//            }
//        }
//
//
//    }
}
