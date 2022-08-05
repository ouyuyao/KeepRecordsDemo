package org.otto.demo.handler;

import io.muserver.MuRequest;
import io.muserver.MuResponse;
import io.muserver.RouteHandler;
import org.otto.demo.bean.ResponseMessage;
import org.otto.demo.utils.Constants;
import org.otto.demo.utils.FileManager;
import org.otto.demo.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class LastestPaymentHandler implements RouteHandler {
    private Logger log= LoggerFactory.getLogger(LastestPaymentHandler.class);

    @Override
    public void handle(MuRequest muRequest, MuResponse muResponse, Map<String, String> map) {
        String currencyCode = muRequest.query().get(Constants.CURRENCY_CODE);
        muResponse.contentType("application/json");
        handleFlow(currencyCode,muResponse);
    }

    public void handleFlow(final String currencyCode,final MuResponse muResponse){
        ResponseMessage responseMessage = new ResponseMessage();
        if (!Utils.checkCurrency(currencyCode)) {
            responseMessage.setResponseCode(Constants.OPT_FAILED);
            responseMessage.setResponseMessage(Constants.INVALID_CURRENCY_CODE);
            responseMessage.setResponseTimeStamp(new SimpleDateFormat(Constants.TIME_FORMATE).format(new Date()));
            muResponse.sendChunk(responseMessage.toString());
        } else {
            try {
                FileManager paymentInfoCache = new FileManager();
                StringBuilder readResult = paymentInfoCache.readFileGetSummary(currencyCode);
                String printData = readResult.lastIndexOf(Constants.LINE_FEED)==readResult.length()-2?readResult.substring(0,readResult.length()-2):readResult.toString();
                if (printData.trim().length()==0){
                    responseMessage.setResponseCode(Constants.OPT_FAILED);
                    responseMessage.setResponseMessage(Constants.NO_DATA);
                    responseMessage.setResponseTimeStamp(new SimpleDateFormat(Constants.TIME_FORMATE).format(new Date()));
                    muResponse.sendChunk(responseMessage.toString());
                }else{
                    responseMessage.setResponseCode(Constants.OPT_SUCCESS);
                    responseMessage.setResponseMessage(printData);
                    responseMessage.setResponseTimeStamp(new SimpleDateFormat(Constants.TIME_FORMATE).format(new Date()));
                    muResponse.sendChunk(responseMessage.toString());
                }
            } catch (Exception e) {
                responseMessage.setResponseCode(Constants.FAILED);
                responseMessage.setResponseMessage(Constants.SYSTEM_ERROR);
                responseMessage.setResponseTimeStamp(new SimpleDateFormat(Constants.TIME_FORMATE).format(new Date()));
                muResponse.sendChunk(responseMessage.toString());
                log.error(Constants.DATA_ISSUE + Constants.COMMON + e.getClass()+Constants.SPACE+e.getMessage());
            }
        }
    }
}
