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
        MuRequest muRequest = PowerMockito.mock(MuRequest.class);
        MuResponse muResponse = PowerMockito.mock(MuResponse.class);
        LastestPaymentHandler lastestPaymentHandler = new LastestPaymentHandler();
        Map<String,Object> param = new LinkedHashMap<String,Object>();
        param.put(Constants.CURRENCY_CODE,"USD");
        List paramList = new ArrayList();
        paramList.add(param);
        muRequest.parameter(paramList.toString());
        lastestPaymentHandler.handle(muRequest,muResponse,null);

        System.out.print(muResponse.outputStream().toString());

    }
}