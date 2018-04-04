package project2;

public class Administrator {
	static String username = "admin";
	static String password = "123456";

	public static boolean Sign_In(String a, String b) {
		if (username.equals(a) & password.equals(b)) {
			return true;
		} else {
			return false;
		}
	}
}
