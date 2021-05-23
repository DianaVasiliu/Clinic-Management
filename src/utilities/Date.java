package utilities;

import java.util.Calendar;
import java.util.Objects;

public class Date implements Comparable<Date> {

    private int day;
    private int month;
    private int year;

    public Date(int day, int month, int year) {
        setYear(year);
        if (this.year != 0) {
            setMonth(month);
        }
        if (this.month != 0) {
            setDay(day);
        }
    }

    public Date(Date date) {
        if (date == null) {
            this.day = 0;
            this.month = 0;
            this.year = 0;
        }
        else {
            this.day = date.day;
            this.month = date.month;
            this.year = date.year;
        }
    }

    @Override
    public int compareTo(Date d2) {
        if (d2 != null) {
            int year1 = this.getYear();
            int year2 = d2.getYear();
            if (year1 < year2) {
                return -1;
            } else if (year1 > year2) {
                return 1;
            } else {
                int month1 = this.getMonth();
                int month2 = d2.getMonth();
                if (month1 < month2) {
                    return -1;
                } else if (month1 > month2) {
                    return 1;
                } else {
                    int day1 = this.getDay();
                    int day2 = d2.getDay();
                    return Integer.compare(day1, day2);
                }
            }
        } else {
            return 0;
        }
    }

    /* setters & getters */

    public void setYear(int year) {
        int todayYear = Calendar.getInstance().get(Calendar.YEAR);
        if (year >= todayYear - 80 && year <= todayYear) {
            this.year = year;
        }
        else {
            System.err.println(Errors.INVALID_YEAR.message);
        }
    }

    public void setMonth(int month) {
        if (month >= 1 && month <= 12) {
            this.month = month;
        }
        else {
            System.err.println(Errors.INVALID_MONTH.message);
        }
    }

    public void setDay(int day) {
        if (validateDay(day)) {
            this.day = day;
        }
        else {
            System.err.println(Errors.INVALID_DAY.message);
        }
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public String getFullDate() {
        return year + "-" + month + "-" + day;
    }

    @Override
    public String toString() {
        return day + "/" + month + "/" + year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Date date = (Date) o;
        return day == date.day && month == date.month && year == date.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, month, year);
    }

    private boolean isLeapYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        return calendar.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
    }

    private boolean validateDay(int day) {
        if (day <= 0) {
            return false;
        }
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            return day <= 31;
        }
        else if (month != 2) {
            return day <= 30;
        }
        else {
            if (isLeapYear(year)) {
                return day <= 29;
            }
            else {
                return day <= 28;
            }
        }
    }
}
