/*
 * Copyright (c) 2014 AsyncHttpClient Project. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at
 *     http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package org.asynchttpclient.netty;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.HttpResponse;

import java.net.SocketAddress;
import java.util.List;

import org.asynchttpclient.AsyncHttpClientConfig;
import org.asynchttpclient.HttpResponseBodyPart;
import org.asynchttpclient.HttpResponseHeaders;
import org.asynchttpclient.HttpResponseStatus;
import org.asynchttpclient.Response;
import org.asynchttpclient.uri.Uri;

/**
 * A class that represent the HTTP response' status line (code + text)
 */
public class NettyResponseStatus extends HttpResponseStatus {

    private final HttpResponse response;
    private final SocketAddress remoteAddress;
    private final SocketAddress localAddress;

    public NettyResponseStatus(Uri uri, AsyncHttpClientConfig config, HttpResponse response, Channel channel) {
        super(uri, config);
        this.response = response;
        if (channel != null) {
            remoteAddress = channel.remoteAddress();
            localAddress = channel.localAddress();
        } else {
            remoteAddress = null;
            localAddress = null;
        }
    }

    @Override
    public Response prepareResponse(HttpResponseHeaders headers, List<HttpResponseBodyPart> bodyParts) {
        return new NettyResponse(this, headers, bodyParts);
    }

    /**
     * Return the response status code
     * 
     * @return the response status code
     */
    public int getStatusCode() {
        return response.getStatus().code();
    }

    /**
     * Return the response status text
     * 
     * @return the response status text
     */
    public String getStatusText() {
        return response.getStatus().reasonPhrase();
    }

    @Override
    public String getProtocolName() {
        return response.getProtocolVersion().protocolName();
    }

    @Override
    public int getProtocolMajorVersion() {
        return response.getProtocolVersion().majorVersion();
    }

    @Override
    public int getProtocolMinorVersion() {
        return response.getProtocolVersion().minorVersion();
    }

    @Override
    public String getProtocolText() {
        return response.getProtocolVersion().text();
    }

    @Override
    public SocketAddress getRemoteAddress() {
        return remoteAddress;
    }

    @Override
    public SocketAddress getLocalAddress() {
        return localAddress;
    }
}
