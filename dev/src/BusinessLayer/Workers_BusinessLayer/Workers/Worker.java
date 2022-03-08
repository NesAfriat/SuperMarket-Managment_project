package BusinessLayer.Workers_BusinessLayer.Workers;

import BusinessLayer.Workers_BusinessLayer.InnerLogicException;
import BusinessLayer.Workers_BusinessLayer.Shifts.*;
import BusinessLayer.Workers_BusinessLayer.WorkersUtils;
import java.util.LinkedList;
import java.util.List;

public class Worker {
    private static final int DEFAULT_MIN_RANGE_TO_ADD_CONSTRAINT = 14;
    private static final int DEFAULT_ID_LENGTH = 9;
    private static final double DEFAULT_MIN_SALARY = 0;

    private String id;
    private String name;
    private String bankAccount;
    private double salary;
    private String educationFund;
    private int vacationDaysPerMonth;
    private int sickDaysPerMonth;
    private String startWorkingDate;
    private String endWorkingDate;
    private List<Job> occupations;
    private List<Constraint> constraints;

    public Worker(String name, String id, String bankAccount, double salary, String educationFund,
                  int vacationDaysPerMonth, int sickDaysPerMonth, String startWorkingDate) throws InnerLogicException {
        if(name == null || id == null || bankAccount == null || educationFund == null || startWorkingDate == null ||
                salary < DEFAULT_MIN_SALARY || sickDaysPerMonth < 0|| vacationDaysPerMonth < 0) {
            throw new InnerLogicException("invalid worker details");
        }
        this.name = name;
        validationLegalId(id);
        this.id = id;
        this.bankAccount = bankAccount;
        this.salary = salary;
        this.educationFund = educationFund;
        this.vacationDaysPerMonth = vacationDaysPerMonth;
        this.sickDaysPerMonth = sickDaysPerMonth;
        this.startWorkingDate = startWorkingDate;
        this.endWorkingDate = null;
        this.constraints = new LinkedList<>();
        this.occupations = new LinkedList<>();
    }

    public Constraint addConstraint(String date, ShiftType shiftType, ConstraintType constraintType) throws InnerLogicException {
        for (Constraint con: constraints) {
            if(con.compareShift(date, shiftType)){
                throw new InnerLogicException("this shift already has constraint");
            }
        }
        WorkersUtils.notPastDateValidation(date);
        if(!WorkersUtils.isDateIsInMoreThanNumDays(date, DEFAULT_MIN_RANGE_TO_ADD_CONSTRAINT)) throw new InnerLogicException("Worker cant add constraint in less then two weeks");
        Constraint con = new Constraint(date, shiftType, constraintType);
        this.constraints.add(con);
        return con;
    }

    public void setConstraints(List<Constraint> constraints) throws InnerLogicException {
        if (constraints == null){
            throw new InnerLogicException("Cannot add a null list of constraints");
        }
        this.constraints = constraints;
    }

    public Constraint removeConstraint(String date, ShiftType shiftType) throws InnerLogicException {
        Constraint output = null;
        for (Constraint con: constraints) {
            if(con.compareShift(date, shiftType)){
                output = con;
                break;
            }
        }
        if(output == null) throw new InnerLogicException("tried to remove non-existing constraint");
        constraints.remove(output);

        return output;
    }

    public boolean canWorkInShift(String date, ShiftType shiftType){
        for (Constraint con: constraints) {
            if(con.compareShift(date, shiftType) && con.getConstraintType() == ConstraintType.Cant){
               return false;
            }
        }
        return endWorkingDate == null;
    }

    public boolean canWorkInJob(Job job){
        return occupations.contains(job) && endWorkingDate == null;
    }

    public void addOccupation(Job job) throws InnerLogicException {
        if (occupations.contains(job)) throw new InnerLogicException("tried to add occupation to worker but he already qualified to work as " + job.name());
        this.occupations.add(job);
    }

    public void removeOccupation(Job job) throws InnerLogicException {
        if (!occupations.contains(job)) throw new InnerLogicException("tried to remove occupation to worker but he was never qualified to work as " + job.name());
        this.occupations.remove(job);

    }

    public void fireWorker(String endWorkingDate) throws InnerLogicException {
        if(this.endWorkingDate != null) throw new InnerLogicException("tried to fire worker that was already fired");
        if(endWorkingDate == null) throw new InnerLogicException("can not set endWorkingDate to null");
        this.endWorkingDate = endWorkingDate;
    }

    public String getId() {
        return id;
    }

    public boolean getIsAdmin(){
        return occupations.contains(Job.HR_Manager) || occupations.contains(Job.Shift_Manager) ;
    }

    public String getName() {
        return this.name;
    }

    public String getBankAccount(){
        return this.bankAccount;
    }

    public double getSalary(){
        return this.salary;
    }

    public String getEducationFund(){
        return this.educationFund;
    }

    public int getVacationDaysPerMonth(){
        return this.vacationDaysPerMonth;
    }

    public int getSickDaysPerMonth(){
        return this.sickDaysPerMonth;
    }

    public String getStartWorkingDate(){
        return this.startWorkingDate;
    }

    public String getEndWorkingDate(){
        return this.endWorkingDate;
    }

    public List<Job> getOccupations(){
        return new LinkedList<Job>(occupations);
    }

    public List<Constraint> getConstraints(){
        LinkedList<Constraint> output =  new LinkedList<Constraint>();
        for (Constraint constraint: constraints) {
            output.add(new Constraint(constraint));
        }
        return output;
    }

    private void validationLegalId(String id) throws InnerLogicException {
        if(id.length() != DEFAULT_ID_LENGTH) throw new InnerLogicException("ID must be in length of 9");
        for(int i = 0; i < id.length(); i++){
            if(id.charAt(i) < '0' || id.charAt(i) > '9') throw new InnerLogicException("ID Can contain only numbers");
        }
    }

    public void setID(String newID) throws InnerLogicException {
        if (newID == null){
            throw new InnerLogicException("cannot set a null ID");
        }
        validationLegalId(newID);
        this.id = newID;
    }

    public void setName(String name) throws InnerLogicException {
        if (name == null){
            throw new InnerLogicException("cannot set a null name");
        }
        this.name = name;
    }

    public void setSalary(double salary) throws InnerLogicException {
        if (salary < DEFAULT_MIN_SALARY){
            throw new InnerLogicException("cannot set salary under minimum");
        }
        this.salary = salary;
    }
}
