package com.github.dockerjava.transport;

import org.osgi.service.component.annotations.ComponentPropertyType;

@ComponentPropertyType
public @interface DockerHttpClientType {

    String TRANSPORT_UNIX_SOCKETS = "unix.sockets";

    String TRANSPORT_WINDOWS_NPIPE = "windows.npipe";

    String TRANSPORT_STDIN_ATTACHMENT = "stdin.attacgment";

    String value();

    String[] transports();
}
