package equipment;

import utilities.Date;
import utilities.LoggingCSV;

import java.time.Duration;
import java.time.LocalDate;

public class NonConsumable extends Equipment {

    private final double daysAfterChange;

    public NonConsumable(String name, double price, Date buyDate, double daysAfterChange) {
        super(name, price, buyDate);
        this.daysAfterChange = daysAfterChange;
    }

    @Override
    public double calculateInvestmentOnMonth(int year, int month) {
        LoggingCSV.log("Calculating investment on month on Nonconsumables");
        double price = this.getPrice();
        Date buyDate = this.getBuyDate();
        LocalDate today = LocalDate.now();
        LocalDate buyDate2 = LocalDate.of(buyDate.getYear(), buyDate.getMonth(), buyDate.getDay());
        if (Duration.between(today, buyDate2).toDays() > daysAfterChange) {
            return price;
        }
        else {
            return 0;
        }
    }

    public double getDaysAfterChange() {
        return daysAfterChange;
    }

    @Override
    public String toString() {
        return "NonConsumable{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", buyDate=" + buyDate +
                ", daysAfterChange=" + daysAfterChange +
                '}';
    }
}
