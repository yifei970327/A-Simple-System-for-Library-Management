package project2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class UI {
	static Scanner input = new Scanner(System.in);
	
	public static void Initial() {///��ʼ����
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

	public static void Administrator_Sign_In() {/// ����Ա��¼
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

	public static String Reader_Sign_In() {/// ���ߵ�¼
		String cardNumber = null;
		System.out.println("Please input your card number");// �����û���
		String s = Reader.Sign_In(input.next());
		if (s!=null){// ���ߵ�¼��֤�ɹ�
			cardNumber = s;
			System.out.println("----------------------The Book(s) You Borrowed------------------");
			Reader.print_booklist(cardNumber);
			System.out.println("--------------------------------------------------------------------------------");
			Reader_Options(cardNumber);
		} else {
			System.out.println("Don't exist!");// �����û�������
			UI.Initial();// ���س�ʼ����
		}
		return cardNumber;
	}

	public static void Administrator_Options() {/// ����Աѡ��
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

	public static void Reader_Options(String cardNumber) {/// ����ѡ��
		System.out.printf("%-32s%-32s%-32s%-32s\n", "1:Consult book in library","2:Borrow , Return , Pay &Renew","3:Print the book I borrowed" ,"4:Back");
		String[] bookInfo=new String[2];
		switch (input.next()) {
		case "1": {
			Consult(cardNumber);// ���߲�ѯ
		}
		case "2": {// ������� ����&����&����&���� ����
			Reader_BNR(cardNumber);
		}
		case "3": {// ��ӡ���߽�����Ϣ
			System.out.println("----------------------The Book(s) You Borrowed------------------");
			Reader.print_booklist(cardNumber);
			System.out.println("--------------------------------------------------------------------------------");
			Reader_Options(cardNumber);
		}
		case "4": {
			UI.Initial();// ���س�ʼ����
		}
		default: {
			System.out.println("Your input is illegal");
			Reader_Options(cardNumber);
		}
		}
	}
	
	public static void Reader_BNR(String cardNumber) {/// ���� ����&����&����&���� ����
		double fine =Reader.calculate_fine(cardNumber);
		System.out.printf("%-32s%-32s%-32s%-32s%-32s\n", "1:Borrow books","2:Retuen books", "3:Pay the fine","4:Renew books","5:Back");
		switch (input.next()) {
		case "1": {//���߽���
			if(fine==0){
				Borrow_book(cardNumber,fine);
			}else{
				System.out.println("You must pay the fine and return overdued book(s) first!");// ��������ҳ��
				pay_the_fine(cardNumber,fine);
			}
		}
		case "2": {// ���߻���
			System.out.println("Please input the book card to Return");
			String returnCard = input.next();
			if(fine==0){
				if(Reader.if_Boorow(cardNumber,returnCard)){
					Reader.Return_book(cardNumber,returnCard);
					Reader_BNR(cardNumber);
				}else{
					System.out.println("You didn't borrow the book!");
					Reader_BNR(cardNumber);
				}
			}else{
				System.out.println("You must pay the fine and return overdued book(s) first!");// ��������ҳ��
				pay_the_fine(cardNumber,fine);
			}
		}
		case "3": {// ���߸����ڷ� �� ����
			if(fine!=0){
				pay_the_fine(cardNumber, fine);
			}else{
				System.out.println("You don't need to pay the fine!");
				Reader_BNR(cardNumber);
			}
		}
		case "4": {// ��������
			if(fine==0){
				System.out.println("Please input the book card to Renew");
				String bookCard=input.next();
				if(Reader.if_Boorow(cardNumber,bookCard)){
					String returnTime=Reader.getReturnTime(cardNumber,bookCard);
					Reader.Renew_books(cardNumber,cardNumber,returnTime);
				}else{
					System.out.println("You didn't borrow the book!");
					Reader_BNR(cardNumber);
				}
			}else{
				System.out.println("You must pay the fine and return overdued book(s) first!");// ��������ҳ��
				pay_the_fine(cardNumber,fine);
			}
		}
		case "5": {// ���ض���ѡ��
			Reader_Options(cardNumber);
		}
		default: {
			System.out.println("Your input is illegal");
			Reader_BNR(cardNumber) ;
		}
		}
	}

	public static void Manage_readers() {/// ����Ա�������
		System.out.printf("%-32s%-32s%-32s\n", "1:New reader", "2:Print readers' imformation", "3:Back");
		switch (input.next()) {
		case "1": {// �����¶���
			System.out.println("Please input the cardnumber , username , sexual and studentnumber");
			Reader.InsertReader(input.next(), input.next(), (input.next().equals("male")?1:0), input.next());
			Manage_readers();
		}
		case "2": {
			Reader.Print_readers();// ��ӡȫ��������Ϣ
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

	public static void Manage_books() {/// ����Ա����ͼ��
		System.out.printf("%-32s%-32s%-32s%-32s\n", "1:Newbook", "2:Editor books", "3:Print information of books",
				"4:Back");
		switch (input.next()) {
		case "1": {// �������
			System.out.println("Please input new book's card , name and price");
			String in1=input.next();
			String in2=input.next();//��Ҫ������ո������
			double in3=input.nextDouble();
			Book.New_book(in1, in2,in3);
			Manage_books();
		}
		case "2": {// �༭ͼ��(�������༭)
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

	public static void Consult(String cardNumber) {/// ���߲�ѯ
		System.out.printf("%-32s%-32s%-32s%-32s\n", "1:Search book by card", "2:Search book by name","3:Search book by key word",
				"4:Back");
		switch (input.next()) {
		case "1": {// ����������
			System.out.println("Please input book card");
			Book.Search_Book_By_Card(input.next());
			Consult(cardNumber);
		}
		case "2": {// ����������
			System.out.println("Please input book name number");
			Book.Search_Book_By_Name(input.next());
			Consult(cardNumber);
		}
		case "3": {// �Թؼ�������
			System.out.println("Please input book key word");
			Book.Search_Book_By_Key_Word(input.next());
			Consult(cardNumber);
		}
		case "4": {// ����������ѡ��
			Reader_Options(cardNumber);
		}
		default: {// �Ƿ�����
			System.out.println("Your input is illegal");
			Consult(cardNumber);
		}
		}
	}
	
	public static void Borrow_book(String cardNumber,double fine) {/// ���߽���
		System.out.println("Please input the book card to borrow");
		String[] bookInfo=Book.Search_Book_By_Card(input.next());
		if(bookInfo[0]!=null){
			System.out.printf("%-32s%-32s\n", "1:Borrow book", "2:Back");
			switch (input.next()) {
			case "1": {// ����
				if (fine == 0) {////������Ƿ��
					if (!bookInfo[2].equals("0")) {////�ݲ�����Ϊ0
						Reader.Borrow_book(cardNumber,bookInfo);
						System.out.println("Succeed to borrow the book!");
					}
				} else {
					System.out.println("You have to pay the fine first!");// ��������ҳ��
					System.out.printf("Total Fine:%d\n",fine);
					pay_the_fine(cardNumber,fine);
				}
				Reader_BNR(cardNumber) ;
			}
			case "2": {//// ���������� ����&����&����&���� ����
				Reader_BNR(cardNumber);
			}
			default: {//// �Ƿ�����
				System.out.println("Your input is illegal");
				Reader_BNR(cardNumber);
			}
			}
		}else{
			System.out.println("Don't exist!");
			Reader_BNR(cardNumber);
		}
	}

	public static void Search_book() {///����Ա���鲢����༭
		System.out.printf("%-32s%-32s%-32s\n", "1:Search book by book card", "2:Search book by book name",
				"3:Back");
		String[] bookInfo = new String[2];
		switch (input.next()) {
		case "1": {/// ���������鲢�༭
			System.out.println("Please input book card");
			bookInfo=Book.Search_Book_By_Card(input.next());
			if(bookInfo[0]!=null){
				Editor_books(bookInfo[0]);
			}else{
			System.out.println("Don't exit!");
			Search_book();
			}
		}
		case "2": {/// ���������鲢�༭
			System.out.println("Please input book name");
			bookInfo=Book.Search_Book_By_Name(input.next());
			if(bookInfo[0]!=null){
				Editor_books(bookInfo[0]);
			}else{
			System.out.println("Don't exit!");
			Search_book();
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

	public static void Editor_books(String bookCard) {///����Աͼ��༭
		System.out.printf("%-32s%-32s%-32s%-32s\n%-32s%-32s%-32s\n", "1:Set book's card ", "2:Set book's name",
				"3:Set book's price", "4:Set book's entertime", "5:Set book's shelf", "6:Set book's situtation",
				"7:Back");
		switch (input.next()) {
		case "1": {
			Book.setBookCard(bookCard);
			Editor_books(bookCard);
		}
		case "2": {
			Book.setBookName(bookCard);
			Editor_books(bookCard);
		}
		case "3": {
			Book.setPrice(bookCard);
			Editor_books(bookCard);
		}
		case "4": {
			Book.setEnterTime(bookCard);
			Editor_books(bookCard);
		}
		case "5": {
			Book.setBookShelf(bookCard);
			Editor_books(bookCard);
		}
		case "6": {
			Book.setSituation(bookCard);
			Editor_books(bookCard);
		}
		case "7": {// ������������ʽ
			Search_book();
		}
		default: {// �Ƿ�����
			System.out.println("Your input is illegal");
			Search_book();
		}
		}
	}

	private static void pay_the_fine(String cardNumber,double fine) {// ����ҳ��
		Connection conn = Reader.getConn();
		System.out.println("------------------------------------Your Overdued Book(s) --------------------------------------");
	    String sql = "select * from reader_book where readercard='"+cardNumber+"'";
    	System.out.printf("%-16s%-16s%-16s%-16s%-16s\n", "BookCard", "BookName", "SendTime", "ReturnTime","RestTime");
		try {
	    	Statement st=conn.createStatement();
	        ResultSet rs = st.executeQuery(sql);
	        while (rs.next()) {
		        System.out.printf("%-16s%-16s%-16s%-16s%-16s\n",rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)) ;
	        }
	        st.close();
	        rs.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		System.out.printf("%-80s%s%s\n","","Total Fine:",fine);
	    System.out.println("--------------------------------------------------------------------------------------------------------------");
		System.out.printf("%-48s%-32s\n", "1:Pay the fine and return the book", "2:Back");
		switch (input.next()) {
		case "1": {
			conn = Reader.getConn();
		    sql = "select * from reader_book where readercard='"+cardNumber+"'";
			try {
		    	Statement st=conn.createStatement();
		        ResultSet rs = st.executeQuery(sql);
		        while (rs.next()) {
		        	if (rs.getInt(6)<0) {
		        		Reader.Return_book(cardNumber, rs.getString(2));
					}
		        }
		        st.close();
		        rs.close();
		        conn.close();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
			System.out.println("Success to pay the fine and return the overdued book(s)!");
			Reader_Options(cardNumber);
		}
		case "2": {
			Reader_Options(cardNumber);
		}
		default: {// �Ƿ�����
			System.out.println("Your input is illegal");
			pay_the_fine(cardNumber,fine);
		}
		}
	}
	
}
