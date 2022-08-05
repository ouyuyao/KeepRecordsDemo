package org.otto.demo.handler;

import io.muserver.MuRequest;
import io.muserver.MuResponse;
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

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PaymentEventHandler.class})
@PowerMockIgnore({"javax.management.*", "javax.net.ssl.*"})
public class PaymentEventHandlerTest {

    @Test
    public void testPaymentEventHandler(){
        MuResponse muResponse = PowerMockito.mock(MuResponse.class);
        PaymentEventHandler paymentEventHandler = new PaymentEventHandler();
        boolean result = paymentEventHandler.handleFlow("USD",muResponse);
        assertTrue(result);
        result = paymentEventHandler.handleFlow("JPY",muResponse); //test with net amount is 0
        assertTrue(result);
        result = paymentEventHandler.handleFlow("SGD",muResponse); //test with no data in txt
        assertTrue(result);
        result = paymentEventHandler.handleFlow("ABC",muResponse);
        assertFalse(result);
        result = paymentEventHandler.handleFlow("abc",muResponse);
        assertFalse(result);
        result = paymentEventHandler.handleFlow("",muResponse);
        assertFalse(result);
    }
}