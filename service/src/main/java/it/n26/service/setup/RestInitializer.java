package it.n26.service.setup;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import static org.slf4j.LoggerFactory.getLogger;

@Component
@ApplicationPath("/api")
public class RestInitializer extends Application {
    private static final Logger LOG = getLogger(RestInitializer.class);

    public RestInitializer() {
        LOG.info("starting rest initializer");
    }

}
