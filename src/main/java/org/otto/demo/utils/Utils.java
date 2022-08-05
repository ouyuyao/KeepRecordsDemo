package org.otto.demo.utils;

import java.util.Currency;
import java.util.Set;

public class Utils {
    public static boolean checkCurrency(String currencyCode) {
        Set<Currency> availableCurrencies = Currency.getAvailableCurrencies();
        boolean checkResult = false;
        try {
            //这里使用java.util.Currency类匹配有效币种和输入的币种（作为支付流程不仅要检查输入币种大小写及内容，还要检查币种是否属于有效币种）
            checkResult = availableCurrencies.contains(Currency.getInstance(currencyCode));
        } catch (Exception e) {
            checkResult = false;
        }
        return checkResult;
    }
    public static boolean checkAmount(String amount) {
        try {
            Double.parseDouble(amount);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
