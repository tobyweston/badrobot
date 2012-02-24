/*
 * Copyright (c) 2011-2012, bad robot (london) ltd
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package bad.robot.http;

import static bad.robot.http.SimpleHeaders.noHeaders;

public class FormUrlEncodedMessage implements HttpPostMessage {

    private final Headers headers;
    private final FormParameters content;

    public FormUrlEncodedMessage(FormParameters content) {
        this(content, noHeaders());
    }

    public FormUrlEncodedMessage(FormParameters content, Headers headers) {
        this.headers = headers;
        this.content = content;
    }

    @Override
    public Headers getHeaders() {
        // Content-Type and Content-Length are already set in Tuples (at least for Apache)
        return headers;
    }

    @Override
    public FormParameters getContent() {
        return content;
    }

}