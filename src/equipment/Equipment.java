package equipment;

import utilities.Date;

public abstract class Equipment {

    protected String name;
    protected double price;
    protected Date buyDate;

    public Equipment(String name, double price, Date buyDate) {
        this.name = name;
        this.price = price;
        this.buyDate = buyDate;
    }

    public abstract double calculateInvestmentOnMonth(int year, int month);

    /* setters & getters */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = new Date(buyDate);
    }

    public double getPrice() {
        return price;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    @Override
    public String toString() {
        return "Equipment   {" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", buyDate=" + buyDate +
                ", type='" + this.getClass().getSimpleName() +
                "'}";
    }
}