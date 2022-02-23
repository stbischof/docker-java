package com.github.dockerjava.transport;

import java.net.URI;

import com.github.dockerjava.jdk.JdkDockerHttpClient;

public class JdkClientTests extends DockerHttpClientTCK {

    @Override
    protected DockerHttpClient createDockerHttpClient(URI dockerHost, SSLConfig sslConfig) {
        return new JdkDockerHttpClient.Builder()
            .dockerHost(dockerHost)
            .sslConfig(sslConfig)
            .build();
    }
}
