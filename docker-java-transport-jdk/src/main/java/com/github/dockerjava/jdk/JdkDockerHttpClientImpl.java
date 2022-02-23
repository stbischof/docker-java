package com.github.dockerjava.jdk;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import com.github.dockerjava.transport.DockerHttpClient;
import com.github.dockerjava.transport.SSLConfig;

class JdkDockerHttpClientImpl implements DockerHttpClient {

    private final HttpClient httpClient;

    private final HttpHost host;

    private final String pathPrefix;

    protected JdkDockerHttpClientImpl(URI dockerHost, SSLConfig sslConfig, int maxConnections,
            Duration connectionTimeout, Duration responseTimeout) {

        switch (dockerHost.getScheme()) {
            case "unix":
            case "npipe":
                pathPrefix = "";
                host = new HttpHost(dockerHost.getScheme(), "localhost", 2375);
                break;
            case "tcp":
                String rawPath = dockerHost.getRawPath();
                pathPrefix = rawPath.endsWith("/") ? rawPath.substring(0, rawPath.length() - 1) : rawPath;
                host = new HttpHost("https", dockerHost.getHost(), dockerHost.getPort());
                break;
            default:
                throw new IllegalArgumentException("Unsupported protocol scheme: " + dockerHost);
        }

        httpClient = HttpClient.newBuilder().build();
    }

    @Override
    public Response execute(Request request) {

        Builder req = HttpRequest.newBuilder();
        req.uri(URI.create(host.getScheme() + ":" + pathPrefix + request.path()));

        request.headers().forEach((k, v) -> req.header(k, v));

        byte[] bodyBytes = request.bodyBytes();
        if (bodyBytes != null) {
            req.method(request.method(), BodyPublishers.ofByteArray(bodyBytes));
        } else {
            InputStream body = request.body();
            if (body != null) {
                req.method(request.method(), BodyPublishers.ofInputStream(() -> body));

            }
        }

        if (request.hijackedInput() != null) {
            // context.setAttribute(HijackingHttpRequestExecutor.HIJACKED_INPUT_ATTRIBUTE, request.hijackedInput());
            req.setHeader("Upgrade", "tcp");
            req.setHeader("Connection", "Upgrade");
        }

        try {
            HttpResponse<InputStream> response = httpClient.send(req.build(), BodyHandlers.ofInputStream());

            return new JdkResponse(response);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws IOException {

    }

    static class JdkResponse implements Response {

        private HttpResponse<InputStream> response;

        JdkResponse(HttpResponse<InputStream> response) {
            this.response = response;
        }

        @Override
        public int getStatusCode() {
            return response.statusCode();
        }

        @Override
        public Map<String, List<String>> getHeaders() {
            return response.headers().map();
        }

        @Override
        public InputStream getBody() {
            return response.body();
        }

        @Override
        public void close() {
            try {
                response.body().close();
            } catch (IOException e) {
                // ignore
            }
        }

    }
}
