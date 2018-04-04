package project2;

import java.util.TimerTask;

public class TimeBy extends TimerTask {
	public void run() {// 当前时间运算
		Reader reader=new Reader();
		reader.rest_time_minus_one();
	}
}
