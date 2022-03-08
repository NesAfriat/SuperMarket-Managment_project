package BusinessLayer.Workers_BusinessLayer.Responses;

import BusinessLayer.Workers_BusinessLayer.Shifts.ShiftType;
import BusinessLayer.Workers_BusinessLayer.Shifts.WorkDay;

public class WorkDayResponse {

    private ShiftResponse morningShift;
    private ShiftResponse eveningShift;
    private String date;

    public WorkDayResponse(WorkDay workDay){

        if (workDay.getShift(ShiftType.Morning) != null){
             this.morningShift = new ShiftResponse(workDay.getShift(ShiftType.Morning));
        }
        else {
            morningShift = null;
        }
        if (workDay.getShift(ShiftType.Evening) != null){
            this.eveningShift = new ShiftResponse(workDay.getShift(ShiftType.Evening));
        }
        else {
            eveningShift = null;
        }

        this.date = workDay.getDate();
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Date: ").append(date).append("\n");
        if (morningShift == null && eveningShift == null){
            stringBuilder.append("There's no shifts at this work day\n");
        }
        if (morningShift != null)
            stringBuilder.append("Morning shift details:\n").append(morningShift.toString());
        if (eveningShift != null)
            stringBuilder.append("\nEvening shift details:\n").append(eveningShift.toString());
        return stringBuilder.toString();
    }

    public String approvedToString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Date: ").append(date).append("\n");
        if (morningShift == null && eveningShift == null){
            stringBuilder.append("There's no shifts at this work day\n");
        }
        if (morningShift != null) {
            if (morningShift.isApproved()) {
                stringBuilder.append("Morning shift details:\n").append(morningShift.toString());
            } else {
                stringBuilder.append("Morning shift is not yet approved\n");
            }
        }
        else {
            stringBuilder.append("There's no morning shift in this day.\n");
        }

        if (eveningShift != null) {
            if (eveningShift.isApproved()) {
                stringBuilder.append("\nEvening shift details:\n").append(eveningShift.toString());
            } else {
                stringBuilder.append("Evening shift is not yet approved\n");
            }
        }
        else {
            stringBuilder.append("There's no evening shift in this day.\n");
        }
        return stringBuilder.toString();
    }

    public String getDate() {
        return date;
    }

    public String Settings() {
        StringBuilder stringBuilder = new StringBuilder();
        if (morningShift != null)
            stringBuilder.append("Morning shift: \n").append(morningShift.Settings()).append("\n");
        else {
            stringBuilder.append("By default there is no morning shift for this work day\n");
        }
        if (eveningShift != null)
            stringBuilder.append("Evening shift: \n").append(eveningShift.Settings()).append("\n");
        else
            stringBuilder.append("By default there is no evening shift for this work day\n");
        return stringBuilder.toString();
    }
}
