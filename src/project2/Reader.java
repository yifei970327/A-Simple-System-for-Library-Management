package project2;

import java.sql.*;
import java.util.Scanner;

public class Reader {
	public Reader(){}
	
	public static Connection getConn() {///Connection��get����
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
	
	public  static void InsertReader(String cardNumber, String userName, int sexual,String studentNumber) {///¼���¶���
		Connection conn = getConn();
		String sql="insert into readers (cardnumber,username,sexual,studentnumber) values('"+cardNumber+"','"+userName+"',"+sexual+",'"+studentNumber+"')";
	    try {
			Statement st=conn.createStatement();
			int i=st.executeUpdate(sql);
	        conn.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	public static String Sign_In(String cardNumber) {/// ���ߵ�¼�ж�(���ؿ��Ż�null)
		Connection conn = getConn();
	    String sql = "select * from readers";
	    String CardNumber=null;
		try {
	    	Statement st=conn.createStatement();
	        ResultSet rs = st.executeQuery(sql);
	        while (rs.next()) {
	        	if(cardNumber.equals(rs.getString(1))){
	        		CardNumber=rs.getString(1);
	        		break;
	        	}
			}
	        st.close();
	        rs.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		return CardNumber;
	}
	
	public static String getReturnTime(String cardNumber,String bookCard){///���ؽ����ϵ�Ļ���ʱ��
		Connection conn = getConn();
	    String sql = "select * from reader_book where readercard='"+cardNumber+"',bookcard='"+bookCard+"'";
	    String returnTime = null;
	    try {
	    	Statement st=conn.createStatement();
	        ResultSet rs = st.executeQuery(sql);
	        returnTime = rs.getString(5);
	        rs.close();
	        st.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return returnTime;
	}	
	
	public static boolean getRenew(String cardNumber,String bookCard){
		Connection conn = getConn();
	    String sql = "select * from reader_book where readercard='"+cardNumber+"',bookcard='"+bookCard+"'";
	    boolean renew = false;
	    try {
	    	Statement st=conn.createStatement();
	        ResultSet rs = st.executeQuery(sql);
	        renew = rs.getBoolean(7);
	        rs.close();
	        st.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return renew;
	}

	public static void Print_readers() {/// ��ӡȫ��������Ϣ
		Connection conn = getConn();
	    String sql = "select * from readers";
		System.out.printf("%-16s%-16s%-16s%-16s\n", "CardNumber", "UserName", "Sexual", "StudentNumber");
		try {
	    	Statement st=conn.createStatement();
	        ResultSet rs = st.executeQuery(sql);
	        while (rs.next()) {
	        	System.out.printf("%-16s%-16s%-16s%-16s\n",rs.getString(1),rs.getString(2),(rs.getInt(3)==1?"male":"female"),rs.getString(4)) ;
			}
	        st.close();
	        rs.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public static void Borrow_book(String cardNumber,String[] bookInfo) {/// ����(���߲�Ƿ���ҹݲز�Ϊ0)
        	Book.bookOut(bookInfo[0]);//�޸�ͼ�� �ݲغͽ������
        	Connection conn = getConn();
    	    String sql = "insert into reader_book (readercard,bookcard,bookname,sendtime,returntime,resttime,renew) "
    	    		+ "values('"+cardNumber+"','"+bookInfo[0]+"','"+bookInfo[1]+"',curdate(), date_add(curdate(),interval '30' day)  ,29,1)";
    	    //ʹ��mysql���ú���  ��������Ϊ��ǰ���ڣ���������Ϊ��ǰ����+30
    		try {
    	    	Statement st=conn.createStatement();
    	    	int i=st.executeUpdate(sql);
    	        st.close();
    	        conn.close();
    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	    }
	}	

	public static void print_booklist(String cardNumber) {/// ��ӡ���߽����嵥
		Connection conn = getConn();
	    String sql = "select * from reader_book where readercard='"+cardNumber+"'";
		try {
	    	Statement st=conn.createStatement();
	        ResultSet rs = st.executeQuery(sql);
        	System.out.printf("%-16s%-16s%-16s%-16s\n", "BookCard", "BookName", "Sendtime", "Returntime");
        	while (rs.next()) {
    	        System.out.printf("%-16s%-16s%-16s%-16s\n",rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)) ;
    		}
	        st.close();
	        rs.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public static boolean if_Boorow(String cardNumber,String returnCard) {/// ��ӡ���߽����嵥
		Connection conn = getConn();
	    String sql = "select * from reader_book where readercard='"+cardNumber+"' and bookcard='"+returnCard+"'";
	    boolean if_borrow=false;
		try {
	    	Statement st=conn.createStatement();
	        ResultSet rs = st.executeQuery(sql);
        	while (rs.next()) {
        		if_borrow=true;
    		}
	        st.close();
	        rs.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		return if_borrow;
	}

	public void rest_time_minus_one() {// ʣ����������
		Connection conn = getConn();
		String sql="update reader_book set resttime =resttime-1";
	    try {
			Statement st=conn.createStatement();
			int i=st.executeUpdate(sql);
	        conn.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	public static double calculate_fine(String cardNumber) {/// ���㷣�� ������ 0 �� �����
		double total = 0;
		Connection conn = getConn();
	    String sql = "select * from reader_book where readercard='"+cardNumber+"'";
		try {
	    	Statement st=conn.createStatement();
	        ResultSet rs = st.executeQuery(sql);
	        while (rs.next()) {
	        	if (rs.getInt(6)<0) {
					total = total - rs.getInt(6);
				}
	        }
	        st.close();
	        rs.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		return total;
	}

	public static void Return_book(String cardNumber,String bookCard) {///���߻���
			Book.bookIn(bookCard);//�޸�ͼ��ݲغͽ������
			Connection conn = getConn();
			String sql="delete from reader_book where readercard='"+cardNumber+"' and bookcard='"+bookCard+"'";//��� ���߽����ϵ
		    try {
				Statement st=conn.createStatement();
				int i=st.executeUpdate(sql);
		        conn.close();
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
	}

	public static void Renew_books(String cardNumber,String bookCard,String returnTime) {// ����(��Ƿ��)
		String sql="update reader_book set returntime=date_add('"+returnTime+"',interval'30' day),resttime=resttime+30,renew=false where readercard='"+cardNumber+"',bookcard='"+bookCard+"'";
	    try {
			Connection conn = getConn();
	    	if(getRenew(cardNumber,bookCard)){			
	    	Statement st=conn.createStatement();
			int i=st.executeUpdate(sql);
	        st.close();
	        conn.close();
	    	}else {
				System.out.println("You can not renew a book twice");
			}
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
