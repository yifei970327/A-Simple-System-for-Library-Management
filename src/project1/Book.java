package project1;

import java.util.ArrayList;
import java.util.Scanner;

public class Book {
	private String BookCard;
	private String BookName;
	private double Price;
	private String EnterTime;
	private String BookShelf;
	public int InLibrary = 0;// 在馆数
	public int LendOut = 0;// 借出数
	Scanner input = new Scanner(System.in);

	public Book(String BookCord, String BookName, double Price) {// 构造方法
		this.BookCard = BookCord;
		this.BookName = BookName;
		this.Price = Price;
		InLibrary = 1;
		EnterTime=UI.now_date[0]+"/"+UI.now_date[1]+"/"+UI.now_date[2];
		System.out.println("New book has registered");
	}

	public String getBookCard() {
		return BookCard;
	}

	public void setBookCard() {
		System.out.println("Please input your new bookcard");
		BookCard = input.next();
	}

	public String getBookName() {
		return BookName;
	}

	public void setBookName() {
		System.out.println("Please input your new bookname");
		BookName = input.next();
	}

	public double getPrice() {
		return Price;
	}

	public void setPrice() {
		System.out.println("Please input your new price");
		Price = input.nextDouble();
	}

	public String getBookShelf() {
		return BookShelf;
	}

	public void setBookShelf() {
		System.out.println("Please input your new bookshelf");
		BookShelf = input.next();
	}

	private String getEnterTime() {
		// TODO Auto-generated method stub
		return EnterTime;
	}

	public void setEnterTime() {
		// TODO Auto-generated method stub
		System.out.println("Please input your new entertime");
		EnterTime = input.next();
	}

	public void setSituation() {// 更改书本数
		// TODO Auto-generated method stub
		System.out.println("Please input number of books in library");
		InLibrary = input.nextInt();
		System.out.println("Please input number of books lend out");
		LendOut = input.nextInt();
	}

	protected static void New_book(String bookCord, String bookName, double price) {// 新书入库
		boolean j = true;
		for (int i = 0; i < UI.Books.size(); i++) {
			if (bookCord.equals(UI.Books.get(i).getBookCard()) & bookName.equals(UI.Books.get(i).getBookName())) {
				UI.Books.get(i).InLibrary = UI.Books.get(i).InLibrary + 1;
				j = false;
				break;
			}
		}
		if (j) {
			UI.Books.add(new Book(bookCord, bookName, price));
		}
	}

	public static void Print_books() {// 列表打印
		// TODO Auto-generated method stub
		System.out.printf("%-32s%-32s%-32s%-32s%-32s%-32s%-32s\n", "BookCard", "BookName", "Price", "EnterTime",
				"BookShelf", "in library books number", "lend out books number");
		for (int i = 0; i < UI.Books.size(); i++) {
			System.out.printf("%-32s%-32s%-32.2f%-32s%-32s%-32d%-32d\n", UI.Books.get(i).getBookCard(),
					UI.Books.get(i).getBookName(), UI.Books.get(i).getPrice(), UI.Books.get(i).getEnterTime(),
					UI.Books.get(i).getBookShelf(), UI.Books.get(i).InLibrary, UI.Books.get(i).LendOut);
		}
	}

	public static void Print_Book(int i) {// 单个打印
		System.out.printf("%-32s%-32s%-32.2f%-32s%-32s%-32d%-32d\n", UI.Books.get(i).getBookCard(),
				UI.Books.get(i).getBookName(), UI.Books.get(i).getPrice(), UI.Books.get(i).getEnterTime(),
				UI.Books.get(i).getBookShelf(), UI.Books.get(i).InLibrary, UI.Books.get(i).LendOut);
	}

	public static boolean Search_book_by_name(ArrayList<Book> books, String keywords) {// 以书名搜书并打印
		// TODO Auto-generated method stub
		boolean j = false;//没有数
		System.out.printf("%-32s%-32s%-32s%-32s%-32s%-32s%-32s\n", "BookCard", "BookName", "Price", "EnterTime",
				"BookShelf", "in library books number", "lend out books number");
		for (int i = 0; i < books.size(); i++) {
			for (int h = 0; h < books.get(i).BookName.length(); h++) {
				for (int t = h + 1; t <= books.get(i).BookName.length(); t++) {
					if (keywords.equals(books.get(i).BookName.substring(h, t))) {
						j = true;//有书
						Print_Book(i);
						break;
					}
				}
			}
		}
		if (!j) {
			System.out.printf("There are no bookname is %s\n", keywords);
		}
		return j;
	}

	public static int Search_book_by_card(String keywords) {// 以书码搜书
		int x = -1;
		for (int i = 0; i < UI.Books.size(); i++) {
			if (keywords.equals(UI.Books.get(i).getBookCard())) {
				x = i;
				break;
			}
		}
		if (x == -1) {
			System.out.printf("There are no bookcord is %s\n", keywords);
		}
		return x;
	}

	public static void Search_book_by_card_and_print(String keywords) {// 以书码搜书并打印
		// TODO Auto-generated method stub
		int t;
		t = Search_book_by_card(keywords);
		if (t >= 0) {
			Book.Print_Book(t);
		}
	}

}
