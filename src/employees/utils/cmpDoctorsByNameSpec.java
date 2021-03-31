package employees.utils;

import employees.Doctor;

import java.util.Comparator;

public class cmpDoctorsByNameSpec implements Comparator<Doctor> {

    @Override
    public int compare(Doctor d1, Doctor d2) {
        if (d1 != null && d2 != null) {
            String fullName1 = d1.getLastName() + d1.getFirstName();
            String fullName2 = d2.getLastName() + d2.getFirstName();
            if (fullName1.compareToIgnoreCase(fullName2) < 0) {
                return -1;
            }
            if (fullName1.compareToIgnoreCase(fullName2) > 0) {
                return 1;
            }
            return d1.getSpecialization().toString().compareToIgnoreCase(d2.getSpecialization().toString());
        }
        else if (d1 == null && d2 == null) {
            return 0;
        }
        else if (d1 == null) {
            return -1;
        }
        else {
            return 1;
        }
    }
}
