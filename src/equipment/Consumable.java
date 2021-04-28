package equipment;

import utilities.Date;
import utilities.LoggingCSV;

import java.time.YearMonth;

public class Consumable extends Equipment {

    private final int numberOfItemsInPackage;
    private final double averageDaysItLasts;

    public Consumable(String name, double price, int number, Date buyDate, double days) {
        super(name, price, buyDate);
        this.numberOfItemsInPackage = number;
        this.averageDaysItLasts = days;
    }

    public double getAverageDaysItLasts() {
        return averageDaysItLasts;
    }

    public int getNumberOfItemsInPackage() {
        return numberOfItemsInPackage;
    }

    @Override
    public double calculateInvestmentOnMonth(int year, int month) {
        LoggingCSV.log("Calculating investment on month on Consumables");
        double price = this.getPrice();
        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();
        int numberOfPackagesRequired = (int) Math.ceil(daysInMonth / averageDaysItLasts);
        return price * numberOfPackagesRequired;
    }

    @Override
    public String toString() {
        return "Consumable{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", buyDate=" + buyDate +
                ", numberOfItemsInPackage=" + numberOfItemsInPackage +
                ", averageDaysItLasts=" + averageDaysItLasts +
                '}';
    }
}
