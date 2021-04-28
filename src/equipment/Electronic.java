package equipment;

import utilities.Date;
import utilities.Errors;
import utilities.LoggingCSV;

import java.time.YearMonth;

public class Electronic extends Equipment {

    private double wattsPerHour;
    private double hoursUsed;
    private static final double wattPrice = 12.0;

    public Electronic(String name, double price, double wattsPerHour, Date buyDate) {
        super(name, price, buyDate);
        this.wattsPerHour = wattsPerHour;
    }

    @Override
    public double calculateInvestmentOnMonth(int year, int month) {
        LoggingCSV.log("Calculating investment on month on Electronics");
        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();
        return wattsPerHour * hoursUsed * wattPrice * daysInMonth;
    }

    public void modifyUsageTime(double amount) {
        if (amount < 0 && this.hoursUsed - amount < 0) {
            System.err.println(Errors.WRONG_AMOUNT);
        }
        else {
            this.hoursUsed += amount;
        }
    }

    public void setWattsPerHour(double wattsPerHour) {
        if (wattsPerHour < 0) {
            System.err.println(Errors.WRONG_AMOUNT);
        }
        else {
            this.wattsPerHour = wattsPerHour;
        }
    }

    public void setHoursUsed(double hoursUsed) {
        if (hoursUsed < 0) {
            System.err.println(Errors.INVALID_DURATION);
        }
        else {
            this.hoursUsed = hoursUsed;
        }
    }

    public double getHoursUsed() {
        return hoursUsed;
    }

    public double getWattsPerHour() {
        return wattsPerHour;
    }

    @Override
    public String toString() {
        return "Electronic  {" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", wattsPerHour=" + wattsPerHour +
                ", buyDate=" + buyDate +
                ", type='" + this.getClass().getSimpleName() +
                "'}";
    }
}
