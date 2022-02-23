package com.github.dockerjava.jdk;

import com.github.dockerjava.transport.SSLConfig;

import java.net.URI;
import java.time.Duration;
import java.util.Objects;

public final class JdkDockerHttpClient extends JdkDockerHttpClientImpl {

    public static final class Builder {

        private URI dockerHost = null;

        private SSLConfig sslConfig = null;

        private int maxConnections = Integer.MAX_VALUE;

        private Duration connectionTimeout;

        private Duration responseTimeout;

        public Builder dockerHost(URI value) {
            this.dockerHost = Objects.requireNonNull(value, "dockerHost");
            return this;
        }

        public Builder sslConfig(SSLConfig value) {
            this.sslConfig = value;
            return this;
        }

        public Builder maxConnections(int value) {
            this.maxConnections = value;
            return this;
        }

        public Builder connectionTimeout(Duration connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
            return this;
        }

        public Builder responseTimeout(Duration responseTimeout) {
            this.responseTimeout = responseTimeout;
            return this;
        }

        public JdkDockerHttpClient build() {
            Objects.requireNonNull(dockerHost, "dockerHost");
            return new JdkDockerHttpClient(dockerHost, sslConfig, maxConnections, connectionTimeout, responseTimeout);
        }
    }

    private JdkDockerHttpClient(URI dockerHost, SSLConfig sslConfig, int maxConnections, Duration connectionTimeout,
        Duration responseTimeout) {
        super(dockerHost, sslConfig, maxConnections, connectionTimeout, responseTimeout);
    }
}
