package patients.utils;

import patients.Prescription;
import utilities.Date;

import java.util.Comparator;

public class cmpPrescriptionDate implements Comparator<Prescription> {

    @Override
    public int compare(Prescription p1, Prescription p2) {
        if (p1 != null && p2 != null) {
            Date date1 = p1.getPrescriptionDate();
            Date date2 = p2.getPrescriptionDate();
            return date1.compareTo(date2);
        }
        else if (p1 == null && p2 == null) {
            return 0;
        }
        else if (p1 == null) {
            return -1;
        }
        else {
            return 1;
        }
    }

}
