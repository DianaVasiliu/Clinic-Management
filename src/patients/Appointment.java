package patients;

import employees.Doctor;
import employees.utils.Specialization;
import utilities.Date;

public class Appointment {

    private static long noOfAppointments;
    private long ID;
    private Date date;
    private Doctor doctor;
    private String time;

    {
        this.ID = ++noOfAppointments;
    }

    public static void decreaseNoOfAppointments() {
        if (noOfAppointments > 0) {
            noOfAppointments--;
        }
    }

    public Appointment(Date date, String time, Doctor doctor) {
        this.date = new Date(date);
        this.time = time;
        this.doctor = new Doctor(doctor);
    }

    public Appointment(Appointment appointment) {
        if (appointment != null) {
            this.ID = appointment.ID;
            this.date = new Date(appointment.date);
            this.doctor = appointment.doctor;
            this.time = appointment.time;
        }
    }

    /* setters & getters */

    public void setDate(Date date) {
        this.date = new Date(date);
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = new Doctor(doctor);
    }

    public void setID(long id) {
        if (id > 0) {
            this.ID = id;
        }
    }

    public Date getDate() {
        return new Date(date);
    }

    public String getTime() {
        return time;
    }

    public Doctor getDoctor() {
        return new Doctor(doctor);
    }

    public long getID() {
        return ID;
    }

    @Override
    public String toString() {
        return "Appointment" +
                "\n\tDoctor:  " + doctor.getFirstName() + " "
                + doctor.getLastName() + ", "
                + Specialization.getSpecString(doctor.getSpecialization()) +
                "\n\tDate:    " + date +
                "\n\tTime:    " + time;
    }
}
