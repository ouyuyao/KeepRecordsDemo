package org.otto.demo.bean;

public class ResponseMessage {
    private int responseCode;
    private String responseMessage;
    private String responseTimeStamp;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseTimeStamp() {
        return responseTimeStamp;
    }

    public void setResponseTimeStamp(String responseTimeStamp) {
        this.responseTimeStamp = responseTimeStamp;
    }

    @Override
    public String toString() {
        return "{" +
                "\"responseCode\":" + responseCode +
                ", \"responseMessage\":\"" + responseMessage + '\"' +
                ", \"responseTimeStamp\":\"" + responseTimeStamp + '\"' +
                '}';
    }
}
