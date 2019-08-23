package com.example.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class StringUtils {
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static String checkNull(Object str) {
        return str != null ? str.toString() : "";
    }

    public static Timestamp checkNullTimestamp(Object str) {
        try {
            return str != null ? new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(str.toString()).getTime())  : null;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
