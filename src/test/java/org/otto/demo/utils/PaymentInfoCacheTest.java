package org.otto.demo.utils;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class PaymentInfoCacheTest extends TestCase {

    @Test
    public void testPaymentInfoCache() {
        FileManager paymentInfoCache = new FileManager();
        Map infoMap = new HashMap();
        infoMap.put("USD","0");
        infoMap.put("GBP","10");
        paymentInfoCache.writeFile(infoMap);
        Map readMap = paymentInfoCache.readFile();
        assertEquals("2433.0",readMap.get("USD"));
    }
}