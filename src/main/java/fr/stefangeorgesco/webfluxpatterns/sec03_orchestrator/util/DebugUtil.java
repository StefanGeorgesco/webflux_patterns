package fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.util;

import fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.dto.OrchestrationRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.databind.ObjectMapper;

public class DebugUtil {

    public static final Logger log = LoggerFactory.getLogger(DebugUtil.class);

    private DebugUtil() {
    }

    public static void logRequestContext(OrchestrationRequestContext ctx) {
        ObjectMapper mapper = new ObjectMapper();
        if (log.isDebugEnabled()) {
            log.debug(mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(ctx));
        }
    }
}
