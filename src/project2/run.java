package project2;

import java.util.Timer;

public class run {

	public static void main(String[] args) {
		System.out.println("Every 10 second counts for 1 day!\n  (It means 5 min after borrowing book it will overdue!)\n"
				+ "-------------------------------------------------------------------");
		Timer timer = new Timer();
		timer.schedule(new TimeBy(), 0, 10000);
		////日期变化 每10秒借书关系中 剩余天数-1，借了书后 5min 后就逾期
		UI.Initial();
	}
}
