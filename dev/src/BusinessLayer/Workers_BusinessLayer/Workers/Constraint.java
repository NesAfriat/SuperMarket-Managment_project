package BusinessLayer.Workers_BusinessLayer.Workers;
import BusinessLayer.Workers_BusinessLayer.Shifts.ShiftType;


public class Constraint {
    private final String date;
    private final ShiftType shiftType;
    private final ConstraintType constraintType;

    public Constraint(String date, ShiftType shiftType, ConstraintType constraintType) {
        this.date = date;
        this.shiftType =shiftType;
        this.constraintType = constraintType;
    }

    public Constraint(Constraint toCopy) {
        this.date = toCopy.date;
        this.shiftType = toCopy.shiftType;
        this.constraintType = toCopy.constraintType;
    }

    public boolean compareShift(String date, ShiftType shiftType){
        return (this.date.equals(date) && this.shiftType == shiftType);
    }

    public ConstraintType getConstraintType() {
        return constraintType;
    }

    public String getDate() {
        return date;
    }

    public ShiftType getShiftType() {
        return shiftType;
    }
}
