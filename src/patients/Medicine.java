package patients;

public class Medicine {

    private String name;
    private String producer;
    private double price;
    private String activeSubstance;
    private int ID;
    private static int noOfMedicine;

    {
        this.ID = ++noOfMedicine;
    }

    public Medicine(String name, String producer, double price, String activeSubstance) {
        this.name = name;
        this.producer = producer;
        this.price = price;
        this.activeSubstance = activeSubstance;
    }

    public Medicine(Medicine medicine) {
        if (medicine != null) {
            this.name = medicine.name;
            this.producer = medicine.producer;
            this.price = medicine.price;
            this.activeSubstance = medicine.activeSubstance;
            this.ID = medicine.ID;
        }
    }

    /* setters & getters */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public int getID() {
        return ID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getActiveSubstance() {
        return activeSubstance;
    }

    public void setActiveSubstance(String activeSubstance) {
        this.activeSubstance = activeSubstance;
    }

    @Override
    public String toString() {
        return "Medicine  {" +
                "ID = " + ID +
                ",  '" + name + '\'' +
                ", producer='" + producer + '\'' +
                ", price=" + price +
                ", activeSubstance='" + activeSubstance + '\'' +
                "}";
    }
}
