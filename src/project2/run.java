package project2;

import java.util.Timer;

public class run {

	public static void main(String[] args) {
		System.out.println("Every 10 second counts for 1 day!\n  (It means 5 min after borrowing book it will overdue!)\n"
				+ "-------------------------------------------------------------------");
		Timer timer = new Timer();
		timer.schedule(new TimeBy(), 0, 10000);
		////���ڱ仯 ÿ10������ϵ�� ʣ������-1��������� 5min �������
		UI.Initial();
	}
}
