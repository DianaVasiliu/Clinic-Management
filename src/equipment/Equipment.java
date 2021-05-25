package equipment;

import utilities.Date;

public abstract class Equipment {

    private long ID;
    private static long noOfEquipment;
    protected String name;
    protected double price;
    protected Date buyDate;

    {
        this.ID = ++noOfEquipment;
    }

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

    public void setID(long ID) {
        this.ID = ID;
    }

    public double getPrice() {
        return price;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public long getID() {
        return ID;
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
