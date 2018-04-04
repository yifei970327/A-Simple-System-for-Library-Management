package project2;

public class Date {
	static int Year;
	static int Month;
	static int Day;

	protected static void DatePlusN(int y, int m, int d, int n) {// 前进N天,结果储存在类中
		Year = y;
		Month = m;
		Day = d;
		for (int i = 1; i <= n; i++) {
			if (Month == 12 && Day == 31) {
				Month = 1;
				Day = 1;
				Year = Year + 1;
			} else {
				Day = Day + 1;
				switch (Day) {
				case 29: {
					if (((Year % 4 != 0) | (Year % 100 == 0 & Year % 400 != 0)) & (Month == 2)) {// 平年2月
						Day = 1;
						Month = 3;
					}
					break;
				}
				case 30: {
					if (!(((Year % 4 != 0) | (Year % 100 == 0 & Year % 400 != 0))) & (Month == 2)) {// 闰年2月
						Day = 1;
						Month = 3;
					}
					break;
				}
				case 31: {
					if ((Month == 4) | (Month == 6) | (Month == 9) | (Month == 11)) {
						Day = 1;
						Month = Month + 1;
					}
					break;
				}
				case 32: {
					Day = 1;
					Month = Month + 1;
					break;
				}
				default: {
					break;
				}
				}
			}
		}
	}

	protected static int getYear() {
		return Year;
	}

	protected static int getMonth() {
		return Month;
	}

	protected static int getDay() {
		return Day;
	}
}
