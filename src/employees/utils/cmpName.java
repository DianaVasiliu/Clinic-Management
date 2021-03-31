package employees.utils;

import employees.Employee;

import java.util.Comparator;

public class cmpName implements Comparator<Employee> {

    @Override
    public int compare(Employee e1, Employee e2) {
        if (e1 != null && e2 != null) {
            String fullName1 = e1.getLastName() + e1.getLastName();
            String fullName2 = e2.getLastName() + e2.getFirstName();
            return Integer.compare(fullName1.compareToIgnoreCase(fullName2), 0);
        }
        else if (e1 == null && e2 == null) {
            return 0;
        }
        else if (e1 == null) {
            return -1;
        }
        else {
            return 1;
        }
    }
}
