package com.belyabl9.incomecalc.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class RoundingUtils {
    
    public static double round(double amount) {
        DecimalFormat decimalFormat = new DecimalFormat(".##");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return Double.parseDouble(decimalFormat.format(BigDecimal.valueOf(amount)));
    }
    
}
