package patients.utils;

import patients.Appointment;
import utilities.Date;

import java.util.Comparator;

public class cmpAppointmentDate implements Comparator<Appointment> {

    @Override
    public int compare(Appointment a1, Appointment a2) {
        if (a1 != null && a2 != null) {
            Date date1 = a1.getDate();
            Date date2 = a2.getDate();
            return date1.compareTo(date2);
        }
        else if (a1 == null && a2 == null) {
            return 0;
        }
        else if (a1 == null) {
            return -1;
        }
        else {
            return 1;
        }
    }
}
