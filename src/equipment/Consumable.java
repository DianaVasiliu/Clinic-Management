package equipment;

import utilities.Date;

import java.time.YearMonth;

public class Consumable extends Equipment {

    private final int numberOfItemsInPackage;
    private double averageDaysItLasts;

    public Consumable(String name, double price, int number, Date buyDate) {
        super(name, price, buyDate);
        this.numberOfItemsInPackage = number;
    }

    @Override
    public double calculateInvestmentOnMonth(int year, int month) {
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
