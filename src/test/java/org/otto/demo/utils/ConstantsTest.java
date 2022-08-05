package org.otto.demo.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConstantsTest {

    @Test
    public void testConstants(){
        assertEquals(",",Constants.COMMON);
        assertEquals(" ",Constants.SPACE);
        assertEquals("\r\n",Constants.LINE_FEED);
        assertEquals("currencyCode",Constants.CURRENCY_CODE);
        assertEquals("amount",Constants.AMOUNT);
        assertEquals("input currency code invalid",Constants.INVALID_CURRENCY_CODE);
        assertEquals("input amount invalid",Constants.INVALID_AMOUNT);
        assertEquals("Handle result:",Constants.HANDLE_RESULT);
        assertEquals(60,Constants.SCHDULE_TASK_SEC);
        assertEquals(0,Constants.SCHDULE_TASK_DELAY_SEC);
        assertEquals("yyyyMMddHHmmssS",Constants.TIME_FORMATE);
        assertEquals("the payment number is :",Constants.PAYMENT_NUM_RESPONSE);
        assertEquals("no data was be found!",Constants.NO_DATA);
        assertEquals("may got data issue, please check the txt file ",Constants.DATA_ISSUE);
        assertEquals("System error ",Constants.SYSTEM_ERROR);
        assertEquals("paymentInfo.txt",Constants.FILENAME);

        assertEquals(200,Constants.OPT_SUCCESS);
        assertEquals(204,Constants.NOT_RECORDS);
        assertEquals(400,Constants.OPT_FAILED);
        assertEquals(500,Constants.FAILED);
    }
}