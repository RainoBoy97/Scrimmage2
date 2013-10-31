package me.rainoboy97.scrimmage.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

	public static String formatIntoHHMMSS(int secs) {
		int remainder = secs % 3600;
		int minutes = remainder / 60;
		int seconds = remainder % 60;

		return new StringBuilder().append(minutes).append(":").append(seconds < 10 ? "0" : "").append(seconds).toString();
	}

	public static String formatIntoHHMMSS(long time) {
		return new SimpleDateFormat("HH:mm:ss").format(new Date(time));
	}
}