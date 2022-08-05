package org.otto.demo.handler;

import io.muserver.MuRequest;
import io.muserver.MuResponse;
import io.muserver.RequestParameters;
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
@PrepareForTest({LastestPaymentHandler.class})
@PowerMockIgnore({"javax.management.*", "javax.net.ssl.*"})
public class LastestPaymentHandlerTest {

    @Test
    public void testLastestPaymentHandler(){
        MuResponse muResponse = PowerMockito.mock(MuResponse.class);
        LastestPaymentHandler lastestPaymentHandler = new LastestPaymentHandler();
        boolean result = lastestPaymentHandler.handleFlow("USD",muResponse);
        assertTrue(result);
        result = lastestPaymentHandler.handleFlow("JPY",muResponse); //test with net amount is 0
        assertTrue(result);
        result = lastestPaymentHandler.handleFlow("SGD",muResponse); //test with no data in txt
        assertTrue(result);
        result = lastestPaymentHandler.handleFlow("ABC",muResponse);
        assertFalse(result);
        result = lastestPaymentHandler.handleFlow("abc",muResponse);
        assertFalse(result);
        result = lastestPaymentHandler.handleFlow("",muResponse);
        assertFalse(result);
    }
}