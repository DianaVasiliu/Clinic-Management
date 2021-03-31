package employees.utils;

import employees.Doctor;

import java.util.Comparator;

public class cmpDoctorsBySpecName implements Comparator<Doctor> {

    @Override
    public int compare(Doctor d1, Doctor d2) {
        if (d1 != null && d2 != null) {

            String spec1 = d1.getSpecialization().toString();
            String spec2 = d2.getSpecialization().toString();
            if (spec1.compareToIgnoreCase(spec2) < 0) {
                return -1;
            }
            if (spec1.compareToIgnoreCase(spec2) > 0) {
                return 1;
            }
            String fullName1 = d1.getLastName() + d1.getFirstName();
            String fullName2 = d2.getLastName() + d2.getFirstName();
            return fullName1.compareToIgnoreCase(fullName2);
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
