package com.github.dockerjava.jdk;

public class HttpHost {

    private String scheme;

    private String host;

    private int port;

    public HttpHost(String scheme, String host, int port) {
        this.scheme = scheme;
        this.host = host;
        this.port = port;

    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
