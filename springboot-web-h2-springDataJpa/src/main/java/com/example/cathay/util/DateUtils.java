package com.example.cathay.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String formatDate(String isoString) {
        try {
            SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
            Date date = isoFormat.parse(isoString);
            SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            return targetFormat.format(date);
        } catch (Exception e) {
            return isoString;
        }
    }
}
