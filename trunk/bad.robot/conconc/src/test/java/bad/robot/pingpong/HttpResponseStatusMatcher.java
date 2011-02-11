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

package bad.robot.pingpong;

import bad.robot.pingpong.transport.ResponseCode;
import org.apache.http.HttpResponse;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class HttpResponseStatusMatcher extends TypeSafeMatcher<HttpResponse> {
    
    private final ResponseCode responseCode;

    public HttpResponseStatusMatcher(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public static HttpResponseStatusMatcher hasStatus(ResponseCode responseCode) {
        return new HttpResponseStatusMatcher(responseCode);
    }

    @Override
    public boolean matchesSafely(HttpResponse response) {
        return response.getStatusLine().getStatusCode() == responseCode.getCode();
    }

    public void describeTo(Description description) {
        description.appendValue(responseCode);
    }
}
