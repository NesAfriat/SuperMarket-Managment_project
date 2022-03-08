package BusinessLayer.Workers_BusinessLayer.Responses;

import BusinessLayer.Workers_BusinessLayer.Shifts.ShiftType;
import BusinessLayer.Workers_BusinessLayer.Workers.Constraint;
import BusinessLayer.Workers_BusinessLayer.Workers.ConstraintType;

public class ConstraintResponse {

    private String date;
    private ShiftType shiftType;
    private ConstraintType constraintType;

    public ConstraintResponse(Constraint constraint){
        this.date = constraint.getDate();
        this.shiftType = constraint.getShiftType();
        this.constraintType = constraint.getConstraintType();
    }

    public String getDate() {
        return date;
    }

    public ConstraintType getConstraintType() {
        return constraintType;
    }

    public ShiftType getShiftType() {
        return shiftType;
    }

    public String toString(){
        return "Date: " + date + " || Shift: " + shiftType + " || Constraint: " + constraintType;
    }
}
