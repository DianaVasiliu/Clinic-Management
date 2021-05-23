package patients;

import utilities.Date;

import java.util.ArrayList;

public class Diagnostic {

    private static long noOfDiagnostics;
    private long ID;
    private String description;
    private ArrayList<Medicine> treatment;
    private Date date;

    {
        noOfDiagnostics++;
        ID = noOfDiagnostics;
    }

    public Diagnostic(String description, ArrayList<Medicine> treatment, Date date) {
        this.description = description;
        this.treatment = new ArrayList<>(treatment);
        this.date = new Date(date);
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Medicine> getTreatment() {
        return treatment;
    }

    public Date getDate() {
        return new Date(date);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTreatment(ArrayList<Medicine> treatment) {
        this.treatment = new ArrayList<>(treatment);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Diagnostic  { " +
                "description='" + description + "'" +
                ",\n\t\t\t\t\ttreatment=" + treatment +
                ",\n\t\t\t\t\tdate=" + date +
                " }";
    }
}
