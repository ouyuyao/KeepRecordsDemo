package org.otto.demo.handler;

import com.sun.deploy.net.HttpResponse;
import io.muserver.MuRequest;
import io.muserver.MuResponse;
import io.muserver.RouteHandler;
import io.netty.handler.codec.http.HttpMethod;
import org.otto.demo.bean.ResponseMessage;
import org.otto.demo.utils.Constants;
import org.otto.demo.utils.FileManager;
import org.otto.demo.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

public class PaymentHandler implements RouteHandler {
    private Logger log= LoggerFactory.getLogger(PaymentHandler.class);

    @Override
    public void handle(MuRequest muRequest, MuResponse muResponse, Map<String, String> map) {
        String currencyCode = muRequest.query().get(Constants.CURRENCY_CODE);
        String amount = muRequest.query().get(Constants.AMOUNT);
        muResponse.contentType("application/json");
        handleFlow(currencyCode,amount,muResponse);
    }

    public void handleFlow(final String currencyCode,final String amount,final MuResponse muResponse){
        ResponseMessage responseMessage = new ResponseMessage();
        if (!Utils.checkCurrency(currencyCode)) {
            responseMessage.setResponseCode(Constants.OPT_FAILED);
            responseMessage.setResponseMessage(Constants.INVALID_CURRENCY_CODE);
            responseMessage.setResponseTimeStamp(new SimpleDateFormat(Constants.TIME_FORMATE).format(new Date()));
            muResponse.sendChunk(responseMessage.toString());
        } else if (!Utils.checkAmount(amount)) {
            responseMessage.setResponseCode(Constants.OPT_FAILED);
            responseMessage.setResponseMessage(Constants.INVALID_AMOUNT);
            responseMessage.setResponseTimeStamp(new SimpleDateFormat(Constants.TIME_FORMATE).format(new Date()));
            muResponse.sendChunk(responseMessage.toString());
        } else {
            try {
                FileManager paymentInfoCache = new FileManager();
                Map<String, String> infoMap = new HashMap<String,String>();
                infoMap.put(currencyCode, amount);
                paymentInfoCache.writeFile(infoMap);
                int random= (int) ((20-10+1)*Math.random()+10);
                String paymentNum = new SimpleDateFormat(Constants.TIME_FORMATE).format(new Date()) + random;
                responseMessage.setResponseCode(Constants.OPT_SUCCESS);
                responseMessage.setResponseMessage(Constants.PAYMENT_NUM_RESPONSE + paymentNum);
                responseMessage.setResponseTimeStamp(new SimpleDateFormat(Constants.TIME_FORMATE).format(new Date()));
                muResponse.sendChunk(responseMessage.toString());
            } catch (Exception e) {
                responseMessage.setResponseCode(Constants.FAILED);
                responseMessage.setResponseMessage(Constants.SYSTEM_ERROR);
                responseMessage.setResponseTimeStamp(new SimpleDateFormat(Constants.TIME_FORMATE).format(new Date()));
                muResponse.sendChunk(responseMessage.toString());
                log.error(e.getClass()+Constants.SPACE+e.getMessage());
            }
        }
    }
}
