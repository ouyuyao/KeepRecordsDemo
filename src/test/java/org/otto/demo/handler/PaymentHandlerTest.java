package org.otto.demo.handler;

import io.muserver.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.otto.demo.utils.Constants;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.muserver.Http2ConfigBuilder.http2EnabledIfAvailable;
import static io.muserver.MuServerBuilder.muServer;
import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PaymentHandler.class})
@PowerMockIgnore({"javax.management.*", "javax.net.ssl.*"})
public class PaymentHandlerTest {

    @Test
    public void testPaymentHandler(){
        MuResponse muResponse = PowerMockito.mock(MuResponse.class);
        PaymentHandler paymentHandler = new PaymentHandler();
        boolean result = paymentHandler.handleFlow("USD","10",muResponse);
        assertTrue(result);
        result = paymentHandler.handleFlow("USD","-10",muResponse);
        assertTrue(result);
        result = paymentHandler.handleFlow("USD","",muResponse);
        assertFalse(result);
        result = paymentHandler.handleFlow("","10",muResponse);
        assertFalse(result);
        result = paymentHandler.handleFlow("ABC","10",muResponse);
        assertFalse(result);
        result = paymentHandler.handleFlow("abc","10",muResponse);
        assertFalse(result);
        result = paymentHandler.handleFlow("USD","asd",muResponse);
        assertFalse(result);
    }
}