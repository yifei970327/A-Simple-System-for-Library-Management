package project1;

import java.util.ArrayList;

public class Reader {
	private String cardnumber;
	private String username;
	private boolean Sexual;// trueΪ��falseΪŮ
	private String studentnumber;
	private String[] bookname = new String[5];// �鱾�嵥
	private String[] bookcard = new String[5];
	private int[][] sendtime = new int[5][3];
	private int[][] returntime = new int[5][3];
	public int[] resttime = new int[5];
	public boolean[] renew = new boolean[5];// �������
	public int i = -1;// �鱾ָ��

	public Reader() {// ȱʡ����
	}

	public Reader(String cardnumber, String username, String sexual, String studentnumber) {// ���췽��
		super();
		this.cardnumber = cardnumber;
		this.username = username;
		if (sexual.equals("male")) {
			Sexual = true;
		} else {
			Sexual = false;
		}
		this.studentnumber = studentnumber;
		System.out.println("New reader has registered");
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public String getUsername() {
		return username;
	}

	public String isSexual() {// ��ȡ�Ա�
		if (Sexual) {
			return "male";
		} else {
			return "female";
		}
	}

	public String getStudentnumber() {
		return studentnumber;
	}

	public static int Sign_In(String a, ArrayList<Reader> b) {// ���ߵ�¼�ж�
		int j = -1;
		for (int i = 0; i < b.size(); i++) {
			if (b.get(i).cardnumber.equals(a)) {
				j = i;
			}
		}
		return j;
	}

	public static void Print_readers(ArrayList<Reader> Readers) {// �б��ӡ������Ϣ
		// TODO Auto-generated method stub
		System.out.printf("%-16s%-16s%-16s%-16s\n", "CardNumber", "UserName", "Sexual", "StudentNumber");
		for (int i = 0; i < Readers.size(); i++) {
			System.out.printf("%-16s%-16s%-16s%-16s\n", Readers.get(i).getCardnumber(), Readers.get(i).getUsername(),
					Readers.get(i).isSexual(), Readers.get(i).getStudentnumber());
		}
	}

	public void Borrow_book(String next) {// ����(�ܽ�)
		// TODO Auto-generated method stubln

		if (UI.Books.get(Book.Search_book_by_card(next)).InLibrary > 0) {
			UI.Books.get(Book.Search_book_by_card(next)).InLibrary--;
			UI.Books.get(Book.Search_book_by_card(next)).LendOut++;
			i = i + 1;
			bookcard[i] = next;
			bookname[i] = UI.Books.get(Book.Search_book_by_card(next)).getBookName();
			sendtime[i][0] = UI.now_date[0];
			sendtime[i][1] = UI.now_date[1];
			sendtime[i][2] = UI.now_date[2];
			Date.DatePlusN(UI.now_date[0], UI.now_date[1], UI.now_date[2], 30);
			returntime[i][0] = Date.getYear();
			returntime[i][1] = Date.getMonth();
			returntime[i][2] = Date.getDay();
			resttime[i] = 29;
			renew[i] = true;
			System.out.println("Succeed borrow");
		} else {
			System.out.println("Sorroy the books have been borrowed by others");
		}
	}

	public void print_booklist() {// �б��ӡ�鵥
		// TODO Auto-generated method stub
		System.out.printf("     %-32s%-32s%-32s%-32s\n", "BookCord", "BookName", "Sendtime", "Returntime");
		if (i >= 0) {
			for (int j = 0; j <= i; j++) {
				System.out.printf("%-5d%-32s%-32s%-32s%-32s\n", j + 1, bookcard[j], bookname[j],
						sendtime[j][0] + "/" + sendtime[j][1] + "/" + sendtime[j][2],
						returntime[j][0] + "/" + returntime[j][1] + "/" + returntime[j][2]);
			}
		}
	}

	public void rest_time_minus_one() {// ʣ����������
		for (int j = 0; j <= i; j++) {
			resttime[j] = resttime[j] - 1;
		}
	}

	public int calculate_fine() {// ���㷣��
		int total = 0;
		if (i >= 0) {
			for (int j = 0; j <= i; j++) {
				if (resttime[j] < 0) {
					total = total - resttime[j];
				}
			}
		}
		return total;
	}

	public void Return_books(int key) {// ����(����ڼ�����)
		// TODO Auto-generated method stub
		if (key - 1 <= i) {
			UI.Books.get(Book.Search_book_by_card(bookcard[key - 1])).InLibrary++;
			UI.Books.get(Book.Search_book_by_card(bookcard[key - 1])).LendOut--;
			for (int j = key - 1; j <= i; j++) {
				for (int h = 0; h <= 2; h++) {
					sendtime[j][h] = sendtime[j + 1][h];
					returntime[j][h] = returntime[j + 1][h];
				}
				resttime[j] = resttime[j + 1];
				bookname[j] = bookname[j + 1];
				bookcard[j] = bookcard[j + 1];
				renew[j] = renew[j + 1];
			}
			i = i - 1;
		} else {
			System.out.println("your input is illegal");// �Ƿ�����
		}
	}

	public void Renew_books(int key) {// ����
		// TODO Auto-generated method stub
		if (key - 1 <= i) {
			if (renew[key - 1]) {
				Date.DatePlusN(returntime[key - 1][0], returntime[key - 1][1], returntime[key - 1][2], 30);
				returntime[key - 1][0] = Date.getYear();
				returntime[key - 1][1] = Date.getMonth();
				returntime[key - 1][2] = Date.getDay();
				resttime[key - 1] = resttime[key - 1] + 30;
				renew[key - 1] = false;
			} else {
				System.out.println("You can not renew a book twice");
			}
		} else {
			System.out.println("your input is illegal");// �Ƿ�����
		}

	}

}
