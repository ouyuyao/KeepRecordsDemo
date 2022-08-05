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
        MuRequest muRequest = PowerMockito.mock(MuRequest.class);
        MuResponse muResponse = PowerMockito.mock(MuResponse.class);
        PaymentHandler paymentHandler = new PaymentHandler();
        muRequest.attribute(Constants.CURRENCY_CODE,"USD");
        muRequest.attribute(Constants.AMOUNT,"10");
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        param.put(Constants.CURRENCY_CODE,"USD");
        param.put(Constants.AMOUNT,"10");
        List paramList = new ArrayList();
        paramList.add(param);
        muRequest.parameter(paramList.toString());
        paymentHandler.handle(muRequest,muResponse,null);
        System.out.print(muResponse.outputStream().toString());
    }
}