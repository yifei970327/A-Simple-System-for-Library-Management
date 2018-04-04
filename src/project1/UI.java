package project1;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class UI {
	static int[] now_date = { 2016, 1, 6 };// ����ʱ��
	static ArrayList<Book> Books = new ArrayList<Book>();
	static ArrayList<Reader> Readers = new ArrayList<Reader>();
	static Scanner input = new Scanner(System.in);
	static int cup;//��ʱ����
	static String cat;//��ʱ����
	static Reader now_reader = new Reader();// ��ǰ����
	Administrator administrator = new Administrator();

	public static void Initial() {// ��ʼ����
		// TODO Auto-generated method stub

		System.out.printf("%-32s%-32s%-32s\n", "1:Administrator sign in", "2:Reader sign in", "3:Exit");
		switch (input.next()) {
		case "1": {// ����Ա��¼
			Administrator_Sign_In();// ����Ա��¼
		}
		case "2": {
			Reader_Sign_In();// ���ߵ�½
		}
		case "3": {// �˳�
			System.exit(0);
		}
		default: {
			System.out.println("Your input is illegal");// �Ƿ�����
			UI.Initial();// ���س�ʼ����
		}
		}
	}

	public static void Administrator_Sign_In() {// ����Ա��¼
		String username;
		String password;
		System.out.println("Please input your username");// �����û���
		username = input.next();
		System.out.println("please input your password");// ��������
		password = input.next();
		if (Administrator.Sign_In(username, password)) {// ����Ա��¼��֤
			Administrator_Options();
		} else {
			System.out.println("Your username or password is wrong");// ����Ա������û�������
			UI.Initial();// ���س�ʼ����
		}
	}

	public static void Reader_Sign_In() {// ���ߵ�¼
		String cardnumber;
		System.out.println("Please input your cardnumber");// �����û���
		cardnumber = input.next();
		if (Reader.Sign_In(cardnumber, Readers) >= 0) {// ���ߵ�¼��֤
			now_reader = Readers.get(Reader.Sign_In(cardnumber, Readers));
			Reader_Options();
		} else {
			System.out.println("Your username is wrong");// �����û�������
			UI.Initial();// ���س�ʼ����
		}
	}

	public static void Administrator_Options() {// ����Աѡ��
		System.out.printf("%-32s%-32s%-32s\n", "1:Manage readers", "2:Manage books", "3:Back");
		switch (input.next()) {
		case "1": {
			Manage_readers();// ���߹���
		}
		case "2": {
			Manage_books();// ͼ�����
		}
		case "3": {
			UI.Initial();// ���س�ʼ����
		}
		default: {
			System.out.println("Your input is illegal");// �Ƿ�����
			Administrator_Options();
		}
		}
	}

	public static void Reader_Options() {// ����ѡ��
		System.out.printf("%-32s%-32s%-32s\n", "1:Consult", "2:My books", "3:Back");
		switch (input.next()) {
		case "1": {
			Consult();// ���߲�ѯ
		}
		case "2": {// ��ѯ�ѽ���ͼ��
			now_reader.print_booklist();
			return_or_renew();
		}
		case "3": {
			UI.Initial();// ���س�ʼ����
		}
		default: {
			System.out.println("Your input is illegal");
			Reader_Options();
		}
		}
	}

	public static void Manage_readers() {// ���߹���
		System.out.printf("%-32s%-32s%-32s\n", "1:New reader", "2:Print readers' imformation", "3:Back");
		switch (input.next()) {
		case "1": {// �����¶���
			System.out.println("Please input your cardnumber,username,sexual and studentnumber");
			Readers.add(new Reader(input.next(), input.next(), input.next(), input.next()));
			Manage_readers();
		}
		case "2": {
			Reader.Print_readers(Readers);// �����б��ӡ
			Manage_readers();
		}
		case "3": {
			Administrator_Options();// ����������Աѡ��
		}
		default: {// �Ƿ�����
			System.out.println("Your input is illegal");
			Manage_readers();
		}
		}
	}

	public static void Manage_books() {// ͼ�����
		System.out.printf("%-32s%-32s%-32s%-32s\n", "1:Newbook", "2:Editor books", "3:Print information of books",
				"4:Back");
		switch (input.next()) {
		case "1": {// �������
			System.out.println("Please input new book's card��name and price");
			Book.New_book(input.next(), input.next(), input.nextDouble());
			Manage_books();
		}
		case "2": {// �༭ͼ��(����)
			Search_book();
		}
		case "3": {// ��ӡͼ��
			Book.Print_books();
			Manage_books();
		}
		case "4": {// ����������Աѡ��
			Administrator_Options();
		}
		default: {// �Ƿ�����
			System.out.println("Your input is illegal");
			Manage_books();
		}
		}
	}

	public static void Consult() {// ���߲�ѯ
		System.out.printf("%-32s%-32s%-32s\n", "1:Search a book by it's name", "2:Search a book by it's card",
				"3:Back");
		switch (input.next()) {
		case "1": {// ����������
			System.out.println("Please input the name of book");
			if (Book.Search_book_by_name(Books, input.next())) {
				Borrow_book();
			}
		}
		case "2": {// ����������
			System.out.println("Please input the card of book");
			Book.Search_book_by_card_and_print(input.next());
			Borrow_book();
		}
		case "3": {// ����������ѡ��
			Reader_Options();
		}
		default: {// �Ƿ�����
			System.out.println("Your input is illegal");
			Consult();
		}
		}
	}

	public static void Search_book() {// �����鱾
		System.out.printf("%-32s%-32s%-32s\n", "1:Search a book by it's name", "2:Search a book by it's card",
				"3:Back");
		switch (input.next()) {
		case "1": {// ����������
			System.out.println("Please input the name of book");
			if (Book.Search_book_by_name(Books, input.next())) {
				System.out.println("Please input the card of book");
				Editor_books(input.nextInt());
			}
		}
		case "2": {// ����������
			System.out.println("Please input the card of book");
			cup = Book.Search_book_by_card(input.next());
			if (cup >= 0) {
				System.out.printf("%-32s%-32s%-32s%-32s%-32s%-32s%-32s\n", "BookCard", "BookName", "Price", "EnterTime",
						"BookShelf", "in library books number", "lend out books number");
				Book.Print_Book(cup);
				Editor_books(cup);
			}
		}
		case "3": {// ������ͼ�����
			Manage_books();
		}
		default: {// �Ƿ�����
			System.out.println("Your input is illegal");
			Search_book();
		}
		}
	}

	public static void Editor_books(int i) {// ͼ��༭
		// TODO Auto-generated method stub
		System.out.printf("%-32s%-32s%-32s%-32s\n%-32s%-32s%-32s\n", "1:Set book's card ", "2:Set book's name",
				"3:Set book's price", "4:Set book's entertime", "5:Set book's shelf", "6:Set book's situtation",
				"7:Back");
		switch (input.next()) {
		case "1": {
			Books.get(cup).setBookCard();
			Editor_books(i);
		}
		case "2": {
			Books.get(cup).setBookName();
			Editor_books(i);
		}
		case "3": {
			Books.get(cup).setPrice();
			Editor_books(i);
		}
		case "4": {
			Books.get(cup).setEnterTime();
			Editor_books(i);
		}
		case "5": {
			Books.get(cup).setBookShelf();
			Editor_books(i);
		}
		case "6": {
			Books.get(cup).setSituation();
			Editor_books(i);
		}
		case "7": {// ������������ʽ
			Search_book();
		}
		default: {// �Ƿ�����
			System.out.println("your input is illegal");
			Search_book();
		}
		}
	}

	public static void Borrow_book() {// �Ƿ����
		System.out.printf("%-32s%-32s\n", "1:Borrow books", "2:Back");
		switch (input.next()) {
		case "1": {// ����
			System.out.println("Please input card of the book you want to borrow");
			cat = input.next();
			if (now_reader.calculate_fine() == 0) {
				if (Book.Search_book_by_card(cat) >= 0) {
					now_reader.Borrow_book(cat);
				}
			} else {
				System.out.println("You have to pay the fine");// ��������ҳ��
				pay_the_fine();
			}
			Borrow_book();
		}
		case "2": {// ��������ѯ
			Consult();
		}
		default: {// �Ƿ�����
			System.out.println("your input is illegal");
			Borrow_book();
		}
		}
	}

	private static void pay_the_fine() {// ����ҳ��
		// TODO Auto-generated method stub
		System.out.printf("You should pay %d\n", now_reader.calculate_fine());
		System.out.printf("%-32s%-32s\n", "1:I have pay the fine and return the book", "2:Back");
		switch (input.next()) {
		case "1": {
			for (int j = 0; j <= now_reader.i; j++) {
				if (now_reader.resttime[j] < 0) {
					now_reader.Return_books(j);
				}
			}
			System.out.println("Account paid");
			return_or_renew();
		}
		case "2": {
			Reader_Options();
		}
		default: {// �Ƿ�����
			System.out.println("your input is illegal");
			pay_the_fine();
		}
		}
	}

	public static void return_or_renew() {// ���������
		// TODO Auto-generated method stub
		// System.out.printf("%-32s","1:","")
		if (now_reader.calculate_fine() == 0) {
			System.out.printf("%-32s%-32s%-32s\n", "1:Return books", "2:Renew the book", "3:Back");
			switch (input.next()) {
			case "1": {
				System.out.println("Please input the number of book you want to return");
				now_reader.Return_books(input.nextInt());
				return_or_renew();
			}
			case "2": {
				System.out.println("Please input the number of book you want to renew");
				now_reader.Renew_books(input.nextInt());
			}
			case "3": {
				Reader_Options();
			}
			default: {// �Ƿ�����
				System.out.println("your input is illegal");
				return_or_renew();
			}
			}
		} else {
			System.out.println("You have to pay the fine");// ��������ҳ��
			pay_the_fine();
		}
	}
}
