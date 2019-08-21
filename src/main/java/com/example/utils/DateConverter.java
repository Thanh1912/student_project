package com.example.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DateConverter {
		
	public static String convertTimestampToString(Timestamp time) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return sdf.format(time);
	}

	public static java.sql.Timestamp getCurrentTimeStamp() {
		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());
	}

}
