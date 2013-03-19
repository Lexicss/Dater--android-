package by.lex.dater;

import java.util.Date;

public class DateInfo {
	private static int MONTHS_IN_YEAR = 12;
	private static double DAYS_IN_YEAR = 365.;
	private static double DAYS_IN_YEAR_AVERAGE = 365.2425;
	private static double HOURS_IN_DAY = 24.;
	private static int MINUTES_IN_HOUR = 60;
	private static int SECONDS_IN_MINUTE = 60;
	private static int NOON = (int) (HOURS_IN_DAY / 2);
	private static int START_CALC_YEAR = -4800; // THE ANCIENST YEAR
	private static double DAYS_IN_MONTH_AVERAGE = 30.6001;
	private static int DAYS_B4800_TILL_JD0 = 32045; // Uneeded years

	private static double JD_FOR_1582 = 2299161.;
	private static double JD_FOR_400 = 1867216.25;

	private static int JULIAN_START_MONTH = 3;
	private static double MINUTES_IN_DAY = HOURS_IN_DAY * MINUTES_IN_HOUR;
	private static double SECONDS_IN_DAY = MINUTES_IN_DAY * SECONDS_IN_MINUTE;

	private static double NEW_YEAR_RA = 6.1;

	public static double getEncodeJD(int year, int month, int day, int hour, int minute, int second) {

		double time = (hour - NOON) / HOURS_IN_DAY + minute / MINUTES_IN_DAY + second / SECONDS_IN_DAY;

		int prevYear = (MONTHS_IN_YEAR - month + JULIAN_START_MONTH - 1) / MONTHS_IN_YEAR;
		// 0 - currentYear overlaps julianYear
		// 1 - currentYear overlaps previous julianYear

		int totalYears = year - START_CALC_YEAR - prevYear;
		// years count from 4800 BD

		int julianMonthNumber = month + MONTHS_IN_YEAR * prevYear - JULIAN_START_MONTH;
		// 0 - March, 1 - April, ... , 11 - February

		int jdn = day + (153 * julianMonthNumber + 2) / 5 + (int) DAYS_IN_YEAR * totalYears
				+ getLipYearsCountTill(totalYears) - DAYS_B4800_TILL_JD0;

		return jdn + time;
	}

	public static double getEncodeJD(int year, int month, int day, int hour, int minute) {
		return getEncodeJD(year, month, day, hour, minute, 0);
	}

	public static double getEncodeJD(int year, int month, int day, int hour) {
		return getEncodeJD(year, month, day, hour, 0, 0);
	}

	public static Date getDecodeJD(double jd) {
		int centuriesCount, d, f;
		double c, e, jd0 = Math.floor(jd + .5);

		if (jd0 < JD_FOR_1582) { // юлианский календарь
			centuriesCount = 0;
			c = jd0 + 1524.;
		} else { // григориаский календарь
			centuriesCount = (int) ((jd0 - JD_FOR_400) / (DAYS_IN_YEAR_AVERAGE * 100)); // centuries from 400
			c = jd0 + (centuriesCount - centuriesCount / 4) + 1525.;// currentDays + lipYears + 1525
		}

		d = (int) ((c - 122.1) / DAYS_IN_YEAR_AVERAGE);// years from JD_0
		e = DAYS_IN_YEAR * d + Math.floor(d / 4.);// days from JD_0
		f = (int) ((c - e) / DAYS_IN_MONTH_AVERAGE);

		int day, month, year, hour, minute, second;
		// 30*6001 * f = days till start of month
		day = (int) (Math.floor(c - e + .5) - Math.floor(DAYS_IN_MONTH_AVERAGE * f));
		month = f - 1 - MONTHS_IN_YEAR * (f / 14);
		year = d - 4715 - (7 + month) / 10;

		double time = jd + .5 - jd0;

		double hourD = HOURS_IN_DAY * time;
		hour = (int) hourD;
		double minuteD = (hourD - hour) * MINUTES_IN_HOUR;
		minute = (int) minuteD;
		double secondD = (minuteD - minute) * SECONDS_IN_MINUTE;
		second = (int) secondD;

		return new Date(year, month - 1, day, hour, minute, second);
	}

	private static int getLipYearsCountTill(int year) {
		return year / 4 - year / 100 + year / 400;
	}

	public static DatePoint convertRA(double ra) {
		if (ra < NEW_YEAR_RA) {
			ra += HOURS_IN_DAY;
		}
		double calcedRa = ra - NEW_YEAR_RA; // Ra from New Year

		double dayAngle = HOURS_IN_DAY / DAYS_IN_YEAR_AVERAGE;

		if (calcedRa <= 31 * dayAngle) {
			return new DatePoint(1, (int) (calcedRa / dayAngle) + 1);
		}
		calcedRa -= 31 * dayAngle;
		if (calcedRa <= 28 * dayAngle) {
			return new DatePoint(2, (int) (calcedRa / dayAngle) + 1);
		}
		calcedRa -= 28 * dayAngle;
		if (calcedRa <= 31 * dayAngle) {
			return new DatePoint(3, (int) (calcedRa / dayAngle) + 1);
		}
		calcedRa -= 31 * dayAngle;
		if (calcedRa <= 30 * dayAngle) {
			return new DatePoint(4, (int) (calcedRa / dayAngle) + 1);
		}
		calcedRa -= 30 * dayAngle;
		if (calcedRa <= 31 * dayAngle) {
			return new DatePoint(5, (int) (calcedRa / dayAngle) + 1);
		}
		calcedRa -= 31 * dayAngle;
		if (calcedRa <= 30 * dayAngle) {
			return new DatePoint(6, (int) (calcedRa / dayAngle) + 1);
		}
		calcedRa -= 30 * dayAngle;
		if (calcedRa <= 31 * dayAngle) {
			return new DatePoint(7, (int) (calcedRa / dayAngle) + 1);
		}
		calcedRa -= 31 * dayAngle;
		if (calcedRa <= 31 * dayAngle) {
			return new DatePoint(8, (int) (calcedRa / dayAngle) + 1);
		}
		calcedRa -= 31 * dayAngle;
		if (calcedRa <= 30 * dayAngle) {
			return new DatePoint(9, (int) (calcedRa / dayAngle) + 1);
		}
		calcedRa -= 30 * dayAngle;
		if (calcedRa <= 31 * dayAngle) {
			return new DatePoint(10, (int) (calcedRa / dayAngle) + 1);
		}
		calcedRa -= 31 * dayAngle;
		if (calcedRa <= 30 * dayAngle) {
			return new DatePoint(11, (int) (calcedRa / dayAngle) + 1);
		}
		calcedRa -= 30 * dayAngle;
		return new DatePoint(12, (int) (calcedRa / dayAngle) + 1);
	}

	public static class DatePoint {
		private int monthNum;
		private int dayNum;

		public int getMonthNum() {
			return monthNum;
		}

		public int getDayNum() {
			return dayNum;
		}

		public DatePoint(int m, int d) {
			if (m < 0 || m > 12) {
				monthNum = 12;
			} else {
				monthNum = m;
			}

			if (d < 0 || d > 31) {
				dayNum = 12;
			} else {
				dayNum = d;
			}
		}

		public String getTimeString() {
			String d = String.valueOf(getDayNum());
			String m;
			if (getMonthNum() < 10) {
				m = "0" + String.valueOf(getMonthNum());
			} else {
				m = String.valueOf(getMonthNum());
			}
			return d + "." + m;
		}

	}
}
