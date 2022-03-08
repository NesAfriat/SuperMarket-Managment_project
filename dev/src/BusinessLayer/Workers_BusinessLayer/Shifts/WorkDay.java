package BusinessLayer.Workers_BusinessLayer.Shifts;

import BusinessLayer.Workers_BusinessLayer.InnerLogicException;
import BusinessLayer.Workers_BusinessLayer.Workers.Job;
import BusinessLayer.Workers_BusinessLayer.Workers.Worker;
import BusinessLayer.Workers_BusinessLayer.WorkersUtils;

public class WorkDay {

    private Shift morningShift;
    private Shift eveningShift;
    private final String date;

    public WorkDay(boolean hasMorning, boolean hasEvening, String date){
        if (hasMorning) morningShift = new Shift();
        if (hasEvening) eveningShift = new Shift();
        this.date = date;
    }

    public Shift addWorker(Job job, Worker worker, ShiftType shiftType) throws InnerLogicException {
        Shift current = getShift(shiftType);
        if (current == null){
            throw new InnerLogicException("This work day does not have a " + shiftType +" shift");
        }

        if (isWorking(worker))
            throw new InnerLogicException(worker.getName() + " is already working at this day");

        if (!worker.canWorkInShift(date,shiftType)){
            throw new InnerLogicException(worker.getName() + " cant work at this shift");
        }
        current.addWorker(job, worker);
        return current;
    }

    public Shift removeShift(ShiftType shiftType) throws InnerLogicException {
        Shift output = null;
        try {
            WorkersUtils.notPastDateValidation(date);
        }catch (InnerLogicException e){
            throw new InnerLogicException("tried to remove shift that already happened");
        }
        if(shiftType == ShiftType.Morning){
            if (morningShift == null) throw new InnerLogicException("tried to remove shift that is not exist");
            output = morningShift;
            morningShift =null;
        }
        else if(shiftType == ShiftType.Evening){
            if (eveningShift == null) throw new InnerLogicException("tried to remove shift that is not exist");
            output = eveningShift;
            eveningShift =null;
        }
        return  output;
    }

    public Shift addShift(ShiftType shiftType) throws InnerLogicException {
        if (ShiftType.Morning == shiftType){
            if (morningShift != null){
                throw new InnerLogicException("This WorkDay already have a morning Shift");
            }else{
                morningShift = new Shift();
                return morningShift;
            }
        }
        else if (ShiftType.Evening.equals(shiftType)) {
            if (eveningShift != null) {
                throw new InnerLogicException("This WorkDay already have an evening Shift");
            }else{
                eveningShift = new Shift();
                return eveningShift;
            }
        }
        throw new InnerLogicException("There's no such shift type");
    }

    public Shift getShift(ShiftType shiftType) {
        if (ShiftType.Morning.equals(shiftType)){
            return morningShift;
        }
        else if (ShiftType.Evening.equals(shiftType)) {
            return eveningShift;
        }
        else
            return null;
    }

    public void removeFromFutureShifts(Worker worker) throws InnerLogicException {
        if (morningShift != null){
            Job role = morningShift.getWorkerRole(worker);
            if (role != null){
                morningShift.removeWorker(role,worker);
            }
        }
        if (eveningShift != null){
            Job role = eveningShift.getWorkerRole(worker);
            if (role != null){
                eveningShift.removeWorker(role,worker);
            }
        }
    }

    public String getDate(){return date;}

    public boolean isWorking(Worker worker) {
        if (morningShift != null && morningShift.isWorking(worker)){
            return true;
        }
        if (eveningShift != null && eveningShift.isWorking(worker)){
            return true;
        }

        return false;
    }
}
