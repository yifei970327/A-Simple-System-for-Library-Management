package project1;

import java.util.TimerTask;

public class TimeBy extends TimerTask {

	@Override
	public void run() {// 当前时间运算
		// TODO Auto-generated method stub
		Date.DatePlusN(UI.now_date[0], UI.now_date[1], UI.now_date[2], 1);
		UI.now_date[0] = Date.getYear();
		UI.now_date[1] = Date.getMonth();
		UI.now_date[2] = Date.getDay();
		for (int g = 0; g < UI.Readers.size(); g++) {
			if (UI.Readers.get(g).i >= 0) {
				UI.Readers.get(g).rest_time_minus_one();
			}
		}
	}
}
