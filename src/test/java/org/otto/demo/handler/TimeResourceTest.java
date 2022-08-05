package org.otto.demo.handler;

import org.junit.Test;

import static org.junit.Assert.*;

public class TimeResourceTest {

    @Test
    public void scheduleReadFileFlow() {
        TimeResource timeResource = new TimeResource();
        timeResource.scheduleReadFileFlow();
        boolean result = timeResource.scheduleReadFileFlow();
        assertTrue(result);
    }
}