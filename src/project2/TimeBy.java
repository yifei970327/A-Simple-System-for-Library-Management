package project2;

import java.util.TimerTask;

public class TimeBy extends TimerTask {
	public void run() {// ��ǰʱ������
		Reader reader=new Reader();
		reader.rest_time_minus_one();
	}
}
