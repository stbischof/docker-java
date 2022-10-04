package com.github.dockerjava.test.osgi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.osgi.framework.BundleContext;
import org.osgi.test.common.annotation.InjectBundleContext;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

import com.github.dockerjava.transport.DockerHttpClient;

@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
public class OSGiTests {

    @Test
    public void dockerHttpClientExists(@InjectService(timeout = 1000) DockerHttpClient dockerHttpClient)
            throws Exception {
        System.out.println(dockerHttpClient);
        Assertions.assertThat(dockerHttpClient).isNotNull();
    }

    @Test
    public void bundleContextExists(@InjectBundleContext BundleContext bc) throws Exception {
        System.out.println(bc);
        Assertions.assertThat(bc).isNotNull();
    }

}
