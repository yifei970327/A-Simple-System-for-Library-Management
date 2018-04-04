package project1;

import java.util.Timer;

public class run {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Timer timer = new Timer();
		timer.schedule(new TimeBy(), 1000, 5000);// 日期变化
		UI.Readers.add(new Reader("11510175", "Zhouhan", "male", "001"));
		UI.Readers.add(new Reader("11510170", "Sunyifei", "male", "002"));
		UI.Readers.add(new Reader("11510162", "Yansiwu", "male", "003"));
		UI.Books.add(new Book("Book001", "I love java", 59));
		UI.Books.add(new Book("Book002", "Welcom to java", 57));
		UI.Books.add(new Book("Book003", "Study C++", 53));
		UI.Initial();

	}

}
