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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class Assertions {

    public static void assertNotEmpty(Collection<?> collection) {
        if (collection == null || collection.isEmpty())
            throw new IllegalArgumentException();
    }

    public static void assertNotEmpty(Object... array) {
        if (array == null || array.length < 1)
            throw new IllegalArgumentException();
    }

    public static void assertNotNull(Object... objects) {
        for (Object object : objects)
            if (object == null)
                throw new IllegalArgumentException();
    }

    public static void assertNotNullAndDifferent(Object... objects) {
        if (new HashSet<Object>(Arrays.asList(objects)).size() != objects.length)
            throw new IllegalArgumentException("all arguments must be different");
        for (Object object : objects) 
            if (object == null)
                throw new IllegalArgumentException();
    }

}
