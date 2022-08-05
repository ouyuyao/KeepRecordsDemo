package org.otto.demo;

import io.muserver.Method;
import io.muserver.MuServer;
import io.muserver.rest.RestHandlerBuilder;
import org.otto.demo.handler.LastestPaymentHandler;
import org.otto.demo.handler.PaymentEventHandler;
import org.otto.demo.handler.TimeResource;
import org.otto.demo.handler.PaymentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.muserver.Http2ConfigBuilder.http2EnabledIfAvailable;
import static io.muserver.MuServerBuilder.httpServer;
import static io.muserver.MuServerBuilder.muServer;

public class ApplicationEntry {
    private static Logger log= LoggerFactory.getLogger(ApplicationEntry.class);

    public static void main(String[] args) {

        TimeResource timeResource = new TimeResource();
        timeResource.start();

        MuServer serverForSchemaTask = httpServer()
                .addHandler(RestHandlerBuilder.restHandler(timeResource))
                .start();

        MuServer server = muServer()
                .withHttpPort(8088)
                .withHttp2Config(http2EnabledIfAvailable())
                .addHandler(Method.GET, "/checkPayment", new PaymentHandler())
                .addHandler(Method.GET, "/checkEvent", new PaymentEventHandler())
                .addHandler(Method.GET, "/checkLastestPayment", new LastestPaymentHandler())
                .start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            server.stop();
        }));

        log.info("Started at " + server.uri());
    }
}
