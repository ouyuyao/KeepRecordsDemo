package org.otto.demo.utils;

public class Constants {

    public static String COMMON = ",";
    public static String SPACE = " ";
    public static String LINE_FEED = "\r\n";
    public static String CURRENCY_CODE = "currencyCode";
    public static String AMOUNT = "amount";

    public static String INVALID_CURRENCY_CODE = "input currency code invalid";
    public static String INVALID_AMOUNT = "input amount invalid";

    public static int SCHDULE_TASK_SEC = 5;
    public static int SCHDULE_TASK_DELAY_SEC = 0;

    public static String TIME_FORMATE = "yyyyMMddHHmmssS";

    public static String PAYMENT_NUM_RESPONSE = "the payment number is :";
    public static String NO_DATA = "no data was be found!";
    public static String DATA_ISSUE = "may got data issue, please check the txt file ";
    public static String SYSTEM_ERROR = "System error ";

    public static String FILENAME = "paymentInfo.txt";

    public static int OPT_SUCCESS = 200; //request success and operate success
    public static int OPT_FAILED = 400; //request success and operate failed
    public static int NOT_RECORDS = 204; //request success and no records find
    public static int FAILED = 500; //request failed
}
