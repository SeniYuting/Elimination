package controller.game;

import java.util.Calendar;

public class TimeControl {
	public static int getDetaSeconds(Calendar curren_time,Calendar last_success_swap){
		int result = 0;
		
		int current_hour = curren_time.get(Calendar.HOUR);
		int current_minute = curren_time.get(Calendar.MINUTE);
		int current_second = curren_time.get(Calendar.SECOND);
		int last_hour = last_success_swap.get(Calendar.HOUR);
		int last_minute =last_success_swap.get(Calendar.MINUTE);
		int last_second =last_success_swap.get(Calendar.SECOND);
		result = (current_hour - last_hour) * 3600
				+ (current_minute - last_minute) * 60
				+ (current_second - last_second);
		return result;
	}
}
