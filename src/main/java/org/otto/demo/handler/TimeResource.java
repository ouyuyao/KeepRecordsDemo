package org.otto.demo.handler;

import io.muserver.rest.MuRuntimeDelegate;
import org.otto.demo.utils.Constants;
import org.otto.demo.utils.FileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.*;

@Path("/scheduleTask")
public class TimeResource {
    private Logger log = LoggerFactory.getLogger(TimeResource.class);

    ScheduledThreadPoolExecutor scheduled = new ScheduledThreadPoolExecutor(2);
    Sse sse = MuRuntimeDelegate.createSseFactory();
    SseBroadcaster broadcaster = sse.newBroadcaster();

    @GET
    @Produces("scheduleTask/stream")
    public void registerListener(@Context SseEventSink eventSink) {
        broadcaster.register(eventSink);
    }

    public void start() {
        scheduled.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    FileManager paymentInfoCache = new FileManager();
                    Map<String, String> infoMap = paymentInfoCache.readFile();
                    StringBuilder data = new StringBuilder();
                    for (Map.Entry<String, String> entry : infoMap.entrySet()) {
                        if (0 != Double.parseDouble(entry.getValue())) {
                            data.append(entry.getKey() + "  " + entry.getValue());
                            data.append(Constants.LINE_FEED);
                        }
                    }
                    String printData = data.lastIndexOf(Constants.LINE_FEED) == data.length() - 2 ? data.substring(0, data.length() - 2) : data.toString();
                    broadcaster.broadcast(sse.newEvent(printData));
                    StringBuilder finalPrint = new StringBuilder();
                    finalPrint.append("--------------").append(Constants.LINE_FEED);
                    if (printData.trim().length() == 0) {
                        finalPrint.append(Constants.NO_DATA);
                    } else {
                        finalPrint.append(printData).append(Constants.LINE_FEED);
                    }
                    finalPrint.append("--------------").append(Constants.LINE_FEED);
                    log.info(finalPrint.toString());
                } catch (Exception e) {
                    log.error(Constants.DATA_ISSUE + Constants.COMMON + e.getClass().getSimpleName());
                }
            }
        }, Constants.SCHDULE_TASK_DELAY_SEC, Constants.SCHDULE_TASK_SEC, TimeUnit.SECONDS);

    }
}
