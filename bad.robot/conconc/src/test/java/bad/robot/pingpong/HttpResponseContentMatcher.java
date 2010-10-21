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

package bad.robot.pingpong;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.io.IOException;

public class HttpResponseContentMatcher extends TypeSafeMatcher<HttpResponse> {

    private final String content;

    public static HttpResponseContentMatcher hasContent(String content) {
        return new HttpResponseContentMatcher(content);
    }

    private HttpResponseContentMatcher(String content) {
        this.content = content;
    }

    @Override
    public boolean matchesSafely(HttpResponse response) {
        try {
            String string = IOUtils.toString(response.getEntity().getContent());
            System.out.println("\"" + string + "\"");
            return content.equals(string);
        } catch (IOException e) {
            return false;
        }
    }

    public void describeTo(Description description) {
        description.appendValue(content);
    }
}
