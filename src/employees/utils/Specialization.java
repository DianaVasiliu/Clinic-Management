package employees.utils;

import java.util.HashMap;

public enum Specialization {
    ANESTHESIOLOGY(0),
    CARDIOLOGY(40.0),
    DERMATOLOGY(35),
    EMERGENCY(0),
    ENDOCRINOLOGY(40),
    FAMILY(0),
    GASTROENTEROLOGY(30),
    GENERAL_SURGERY(50),
    GYNECOLOGY(35),
    NEPHROLOGY(45),
    NEUROLOGY(50),
    NEUROSURGERY(50),
    OPHTALMOLOGY(30),
    ORTHOPEDIC_SURGERY(30),
    OTOLARYGOLOGY(25),
    PEDIATRICS(15),
    PSYCHIATRY(55),
    RADIOLOGY(50),
    UROLOGY(45),
    UNKNOWN(0);

    double consultationPrice;
    private static final HashMap<String, Specialization> map;

    static {
        map = new HashMap<>();
        for (Specialization specialization : Specialization.values()) {
            map.put(specialization.toString(), specialization);
        }
    }

    Specialization(double price) {
        this.consultationPrice = price;
    }

    public double getConsultationPrice() {
        return consultationPrice;
    }

    public static Specialization getSpecialization(String name) {
        name = name.toUpperCase().replace(" ", "_");
        return map.get(name);
    }

    public static String getSpecString(Specialization specialization) {
        if (specialization != null) {
            return specialization.toString();
        }
        else {
            return "";
        }
    }

}
