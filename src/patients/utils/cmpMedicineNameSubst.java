package patients.utils;

import patients.Medicine;

import java.util.Comparator;

public class cmpMedicineNameSubst implements Comparator<Medicine> {

    @Override
    public int compare(Medicine m1, Medicine m2) {
        if (m1 != null && m2 != null) {
            String name1 = m1.getName();
            String name2 = m2.getName();
            if (name1.compareToIgnoreCase(name2) < 0) {
                return -1;
            } else if (name1.compareToIgnoreCase(name2) > 0) {
                return 1;
            } else {
                String subst1 = m1.getActiveSubstance();
                String subst2 = m2.getActiveSubstance();
                if (subst1.compareToIgnoreCase(subst2) < 0) {
                    return -1;
                } else if (subst1.compareToIgnoreCase(subst2) > 0) {
                    return 1;
                } else {
                    double price1 = m1.getPrice();
                    double price2 = m2.getPrice();
                    return Double.compare(price1, price2);
                }
            }
        }
        else if (m1 == null && m2 == null) {
            return 0;
        }
        else if (m1 == null) {
            return -1;
        }
        else {
            return 1;
        }
    }
}
