package project2;

import java.sql.*;
import java.util.Scanner;

public class Book {
	static Scanner input = new Scanner(System.in);
	
	private static Connection getConn() {///Connection��get����
	    String driver = "com.mysql.jdbc.Driver";
	    String url = "jdbc:mysql://localhost:3306/library";
	    String username = "root";
	    String password = "";
	    Connection conn = null;
	    try {
	        Class.forName(driver); 
	        conn = DriverManager.getConnection(url, username, password);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return conn;
	}

	public  static void InsertBook(String bookCard, String bookName, double price) {///���������ݿ�
		Connection conn = getConn();
		String sql="insert into books (bookcard,bookname,price,entertime,inlibrary,lendout) values('"+bookCard+"','"+bookName+"',"+price+",curdate(),1,0)";
	    try {
			Statement st=conn.createStatement();
			int i=st.executeUpdate(sql);
	        conn.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public static void InsertBook(String bookCard, String bookName, double price,String enterTime,String bookShelf,int inLibrary,int lendOut) {//���������ݿ�(ȫ������)
		Connection conn = getConn();
		String sql="insert into books (bookcard,bookname,price,entertime,bookshelf,inlibrary,lendout) values('"+bookCard+"','"+bookName+"',"+price+",'"+enterTime+"','"+bookShelf+"',"+inLibrary+","+lendOut+")";
	    PreparedStatement pstmt;
	    try {
	    	Statement st=conn.createStatement();
			int i=st.executeUpdate(sql);
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	}

	public static void setBookCard(String oldBookCard) {///�޸����루����Ϊ���������룬inputΪ�����룩
		System.out.println("Please input the new bookcard");
		String newCard = input.next();
	    Connection conn = getConn();
	    String sql = "update books set bookcard='"+newCard+"' where bookcard='"+oldBookCard+"'";
	    try {
	    	Statement st=conn.createStatement();
	        int x=st.executeUpdate(sql);
	        st.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public static void setBookName(String oldBookCard) {///�޸�����������Ϊ���������룬inputΪ�����룩
		System.out.println("Please input the new bookname");
		String newName = input.nextLine();
	    Connection conn = getConn();
	    String sql = "update books set bookname='"+newName+"' where bookcard='"+oldBookCard+"'";
	    try {
	    	Statement st=conn.createStatement();
	        int x=st.executeUpdate(sql);
	        st.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	}

	public static void setPrice(String bookCard) {///�޸�ͼ��۸񣨲���Ϊ�������룩
		System.out.println("Please input the new price");
	    Connection conn = getConn();
	    String sql = "update books set price='"+input.nextDouble()+"' where bookcard='"+bookCard+"'";
	    try {
	    	Statement st=conn.createStatement();
	        int x=st.executeUpdate(sql);
	        st.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public static void setBookShelf(String bookCard) {///�޸�ͼ����ܣ�����Ϊ�������룩
		System.out.println("Please input the new book shelf");
	    Connection conn = getConn();
	    String sql = "update books set bookshelf='"+input.next()+"' where bookcard='"+bookCard+"'";
	    try {
	    	Statement st=conn.createStatement();
	        int x=st.executeUpdate(sql);
	        st.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public static void setEnterTime(String bookCard) {///�޸�ͼ�����ʱ�䣨����Ϊ�������룩
		System.out.println("Please input the new enter time");
	    Connection conn = getConn();
	    String sql = "update books set entertime='"+input.next()+"' where bookcard='"+bookCard+"'";
	    try {
	    	Statement st=conn.createStatement();
	        int x=st.executeUpdate(sql);
	        st.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public static void setSituation(String bookCard) {///�޸�ͼ�����ʱ�䣨����Ϊ�������룩
		System.out.println("Please input the new number in library");
		int in=input.nextInt();
		System.out.println("Please input the new number lend out");
		int out=input.nextInt();
	    Connection conn = getConn();
	    String sql = "update books set inlibrary='"+in+"',lendout='"+out+"' where bookcard='"+bookCard+"'";
	    try {
	    	Statement st=conn.createStatement();
	        int x=st.executeUpdate(sql);
	        st.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();	    	
	    }
	    
	}

	public static int getInLibrary(String bookCard){///����ͼ��ݲ�����
		Connection conn = getConn();
	    String sql = "select * from books where bookcard='"+bookCard+"'";
	    int inLibrary =0;
	    try {
	    	Statement st=conn.createStatement();
	        ResultSet rs = st.executeQuery(sql);
	        inLibrary = rs.getInt(6);
	        rs.close();
	        st.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return inLibrary;
	}

	public static void bookOut(String bookCard){///����ʱ �޸�ͼ�� �ݲ�-1,���+1
	    Connection conn = getConn();
	    String sql = "update books set inlibrary=inlibrary-1,lendout=lendout+1 where bookcard='"+bookCard+"'";
	    try {
	    	Statement st=conn.createStatement();
	        int x=st.executeUpdate(sql);
	        st.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void bookIn(String bookCard){///����ʱ �޸�ͼ�� �ݲ�+1,���-1
	    Connection conn = getConn();
	    String sql = "update books set inlibrary=inlibrary+1,lendout=lendout-1 where bookcard='"+bookCard+"'";
	    try {
	    	Statement st=conn.createStatement();
	        int x=st.executeUpdate(sql);
	        st.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void New_book(String bookCard, String bookName, double price) {/// �������(�ж��Ƿ������)
		    Connection conn = getConn();
		    String sql = "select * from books";
		    try {
		    	Statement st=conn.createStatement();
		        ResultSet rs = st.executeQuery(sql);
		        boolean j = true;
		        while (rs.next()) {
		                if (bookCard.equals(rs.getString(1)) & bookName.equals(rs.getString(2))) {//��ͼ���Ѵ��ڣ���ݲ���+1
		                	sql = "update books set inlibrary=inlibrary+1 where bookcard='"+bookCard+"'";
		        			int x=st.executeUpdate(sql);
		                	break;
		                }
		        }
				if (j) {//��ͼ�鲻���ڣ������������ݿ�
					 InsertBook(bookCard, bookName, price);
				}
		        st.close();
		        rs.close();
		        conn.close();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
	
	}

	public static void Print_books() {///��ӡȫ��ͼ����Ϣ
		Connection conn = getConn();
	    String sql = "select * from books";
		System.out.printf("%-16s%-16s%-16s%-16s%-16s%-16s%-16s\n", "BookCard", "BookName", "Price", "EnterTime",
				"BookShelf", "In library", "Lend out");
		try {
	    	Statement st=conn.createStatement();
	        ResultSet rs = st.executeQuery(sql);
	        while (rs.next()) {
	        	System.out.printf("%-16s%-16s%-16.2f%-16s%-16s%-16d%-16d\n",rs.getString(1),rs.getString(2),rs.getDouble(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getInt(7)) ;
			}
	        st.close();
	        rs.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public static String[] Search_Book_By_Card(String bookCard) {///�����ѯ��������ӡ(���Ҳ��� ����ʾ������)
		Connection conn = getConn();
	    String sql = "select * from books where bookcard='"+bookCard+"'";
	    String[] bookInfo=new String[3];
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    	System.out.printf("%-16s%-16s%-16s%-16s%-16s%-16s%-16s\n", "BookCard", "BookName", "Price", "EnterTime",
    			"BookShelf", "In library", "Lend out");
		try {
	    	Statement st=conn.createStatement();
	        ResultSet rs = st.executeQuery(sql);
	        while (rs.next()) {
	        	bookInfo[0]=rs.getString(1);
	        	bookInfo[1]=rs.getString(2);
	        	bookInfo[2]=rs.getString(6);
	        	System.out.printf("%-16s%-16s%-16.2f%-16s%-16s%-16d%-16d\n",rs.getString(1),rs.getString(2),rs.getDouble(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getInt(7)) ;
	        	break;
			}
	        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
	        st.close();
	        rs.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		return bookInfo;
	}

	public static String[] Search_Book_By_Name(String bookName) {///������ѯ��������ӡ
		Connection conn = getConn();
	    String sql = "select * from books where bookname='"+bookName+"'";
	    String[] bookInfo=new String[3];
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    	System.out.printf("%-16s%-16s%-16s%-16s%-16s%-16s%-16s\n", "BookCard", "BookName", "Price", "EnterTime",
    			"BookShelf", "In library", "Lend out");
		try {
	    	Statement st=conn.createStatement();
	        ResultSet rs = st.executeQuery(sql);
	        while (rs.next()) {
	        	bookInfo[0]=rs.getString(1);
	        	bookInfo[1]=rs.getString(2);
	        	bookInfo[2]=rs.getString(6);
	        	System.out.printf("%-16s%-16s%-16.2f%-16s%-16s%-16d%-16d\n",rs.getString(1),rs.getString(2),rs.getDouble(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getInt(7)) ;
	        	break;
			}
	        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
	        st.close();
	        rs.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		return bookInfo;
	}
	
	public static void Search_Book_By_Key_Word(String keyWord) {///ͼ��ؼ��ֲ�ѯ��������ӡ(���������)
		Connection conn = getConn();
	    String sql = "select * from books where bookname like '%"+keyWord+"%'";
		try {
	    	Statement st=conn.createStatement();
	        ResultSet rs = st.executeQuery(sql);
	        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
        	System.out.printf("%-16s%-16s%-16s%-16s%-16s%-16s%-16s\n", "BookCard", "BookName", "Price", "EnterTime",
        			"BookShelf", "In library", "Lend out");
	        while (rs.next()) {
	        	System.out.printf("%-16s%-16s%-16.2f%-16s%-16s%-16d%-16d\n",rs.getString(1),rs.getString(2),rs.getDouble(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getInt(7)) ;
	        }
	        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
	        rs.close();
	        st.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
}
