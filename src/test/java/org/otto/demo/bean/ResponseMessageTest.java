package org.otto.demo.bean;

import org.junit.Test;
import org.otto.demo.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class ResponseMessageTest {

    @Test
    public void testResponseMessage(){
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setResponseCode(Constants.OPT_SUCCESS);
        responseMessage.setResponseMessage("test message");
        responseMessage.setResponseTimeStamp("20220805112113234");
        assertEquals(Constants.OPT_SUCCESS,responseMessage.getResponseCode());
        assertEquals("test message",responseMessage.getResponseMessage());
        assertEquals("20220805112113234",responseMessage.getResponseTimeStamp());
    }
}