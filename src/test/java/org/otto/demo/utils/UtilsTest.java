package org.otto.demo.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class UtilsTest {

    @Test
    public void checkCurrency() {
        String currencyCode = "USD";
        assertTrue(Utils.checkCurrency(currencyCode));
        currencyCode = "ABC";
        assertFalse(Utils.checkCurrency(currencyCode));
    }

    @Test
    public void checkAmount() {
        String amount = "123";
        assertTrue(Utils.checkAmount(amount));
        amount = "qwe";
        assertFalse(Utils.checkAmount(amount));
    }
}