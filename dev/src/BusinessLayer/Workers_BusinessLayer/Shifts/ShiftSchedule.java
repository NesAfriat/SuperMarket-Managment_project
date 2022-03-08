package BusinessLayer.Workers_BusinessLayer.Shifts;

import BusinessLayer.Workers_BusinessLayer.InnerLogicException;
import BusinessLayer.Workers_BusinessLayer.Pair;
import BusinessLayer.Workers_BusinessLayer.Workers.Job;
import BusinessLayer.Workers_BusinessLayer.WorkersUtils;
import DataLayer.Workers_DAL.WorkerDataController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class ShiftSchedule {
    private Map<String, WorkDay> workDays;
    private DefaultWorkDayHolder defaultWorkDayHolder;
    private List<Pair> requestList;
    private WorkerDataController workerDataController;

    public ShiftSchedule(){
        workDays = new HashMap<>();
        defaultWorkDayHolder = new DefaultWorkDayHolder();
        workerDataController = new WorkerDataController();
        requestList = workerDataController.getAllRequests();
    }


    public WorkDay getWorkDay(String date) throws InnerLogicException {
        WorkDay workDay = workDays.get(date);
        if (workDay == null){
            workDay = workerDataController.getWorkDay(date);
            if(workDay != null) workDays.put(date,workDay);
        }
        return workDay;
    }

    public List<WorkDay> getWorkDaysFrom(String date) {
        List<WorkDay> futureDays = workerDataController.getWorkDaysFromDate(date);
        for (WorkDay workDay: futureDays) {
            if(!workDays.containsValue(workDay)){
                workDays.put(workDay.getDate(),workDay);
            }
        }
        return futureDays;
    }

    public void setDefaultJobsInShift(int day, ShiftType shiftType, Job job, int amount) throws InnerLogicException {
        defaultWorkDayHolder.setDefaultJobInShift(day, shiftType, job, amount);
    }

    public Shift getDefaultShiftSkeleton(int day, ShiftType shiftType) throws InnerLogicException {
        Shift skeleton = new Shift();
        List<Job> shiftWorkersRoles = WorkersUtils.getShiftWorkers();
        for (Job role: shiftWorkersRoles){
            skeleton.addRequiredJob(role, defaultWorkDayHolder.getDefaultJobInShift(role, day, shiftType));
        }
        return skeleton;
    }

    public WorkDay getDefaultWorkDaySkeleton(int day) throws InnerLogicException {
        boolean hasMorning = defaultWorkDayHolder.getDefaultShiftInDay(day, ShiftType.Morning);
        boolean hasEvening = defaultWorkDayHolder.getDefaultShiftInDay(day, ShiftType.Evening);
        WorkDay skeleton = new WorkDay(hasMorning, hasEvening,"");
        List<Job> shiftWorkersRoles = WorkersUtils.getShiftWorkers();
        Shift morningSkeleton = skeleton.getShift(ShiftType.Morning);
        Shift eveningSkeleton = skeleton.getShift(ShiftType.Evening);
        for (Job role: shiftWorkersRoles){
            if(morningSkeleton != null)
                morningSkeleton.addRequiredJob(role, defaultWorkDayHolder.getDefaultJobInShift(role, day, ShiftType.Morning));
            if(eveningSkeleton != null)
                eveningSkeleton.addRequiredJob(role, defaultWorkDayHolder.getDefaultJobInShift(role, day, ShiftType.Evening));
        }
        return skeleton;
    }

    public void setDefaultShiftInDay(int dayOfTheWeek, ShiftType shiftType, boolean changeTo) throws InnerLogicException {
        defaultWorkDayHolder.setDefaultShiftInDay(dayOfTheWeek, shiftType, changeTo);
    }

    public WorkDay addDefaultShift(String date, ShiftType shiftType) throws InnerLogicException {
        WorkDay workDay = getWorkDay(date);
        if (workDay != null && workDay.getShift(shiftType) != null){
            throw new InnerLogicException("Tried to add shift to a workday that already has the shift");
        }
        Shift shift;
        int dayOfTheWeek = WorkersUtils.getWeekDayFromDate(date);
        if (workDay == null){
            boolean hasMorning = shiftType.equals(ShiftType.Morning);
            boolean hasEvening = shiftType.equals(ShiftType.Evening);
            workDay = new WorkDay(hasMorning, hasEvening, date);
            workDays.put(date, workDay);
            workerDataController.addWorkDay(workDay);
            shift = workDay.getShift(shiftType);
        }
        else {
            shift = workDay.addShift(shiftType);
        }
        List<Job> shiftWorkersRoles = WorkersUtils.getShiftWorkers();
        for (Job role: shiftWorkersRoles){
            shift.addRequiredJob(role, defaultWorkDayHolder.getDefaultJobInShift(role, dayOfTheWeek, shiftType));
        }

        return workDay;
    }

    public WorkDay addDefaultWorkDay(String date) throws InnerLogicException {
        if (workDays.containsKey(date) || workerDataController.getWorkDay(date) != null){
            throw new InnerLogicException("Workday at date: " + date + " is already exist");
        }
        int dayOfTheWeek = WorkersUtils.getWeekDayFromDate(date);
        boolean hasMorning = defaultWorkDayHolder.getDefaultShiftInDay(dayOfTheWeek, ShiftType.Morning);
        boolean hasEvening = defaultWorkDayHolder.getDefaultShiftInDay(dayOfTheWeek, ShiftType.Evening);
        WorkDay workDay = new WorkDay(hasMorning,hasEvening,date);
        List<Job> shiftWorkersRoles = WorkersUtils.getShiftWorkers();

        if (hasMorning){
            Shift shift = workDay.getShift(ShiftType.Morning);
            for (Job role: shiftWorkersRoles){
                shift.addRequiredJob(role, defaultWorkDayHolder.getDefaultJobInShift(role, dayOfTheWeek, ShiftType.Morning));
            }
        }

        if (hasEvening){
            Shift shift = workDay.getShift(ShiftType.Evening);
            for (Job role: shiftWorkersRoles){
                shift.addRequiredJob(role, defaultWorkDayHolder.getDefaultJobInShift(role, dayOfTheWeek, ShiftType.Evening));
            }
        }
        workDays.put(date,workDay);
        workerDataController.addWorkDay(workDay);
        return workDay;
    }

    public void  addRequest(int OrderID, String date) throws InnerLogicException {
        boolean canAdd = true;
        for (Pair pair: requestList) {
            if(pair.getKey() == OrderID){
                // cannot add request if same order id already in the system within less than a week range
                if(!WorkersUtils.dateDifferenceGreaterThen7(pair.getValue(), date)){
                    canAdd = false;
                }
            }
        }
        if(canAdd){
            requestList.add(new Pair(OrderID, date));
            workerDataController.addRequest(OrderID, date);
        }
        else{
            throw new InnerLogicException("this Request is already in the system");
        }
    }

    public void  removeRequest(int OrderID, String date) throws InnerLogicException {
        WorkersUtils.dateValidation(date);
        Pair toRemove = null;
        for (Pair pair: requestList) {
            if(pair.getKey() == OrderID && pair.getValue().equals(date)){
                toRemove = pair;
                break;
            }
        }
        if(toRemove != null){
            requestList.remove(toRemove);
            workerDataController.removeRequest(OrderID, date);
        }
        else{
            throw new InnerLogicException("this Request is not in the system");
        }

    }

    public List<Pair>  getRequests() {
        return new LinkedList<>(requestList);
    }

    private class DefaultWorkDayHolder{

        static final int weekDayMorning = 0;
        static final int weekDayEvening = 1;
        static final int fridayMorning = 2;
        static final int fridayEvening = 3;
        static final int saturdayMorning = 4;
        static final int saturdayEvening = 5;
        static final int DEFAULT_SHIFT_MANAGER_AMOUNT = 1;

        private WorkerDataController workerDataController = new WorkerDataController();
        private final Map<Job, int[]> defaultShiftSetup;
        private final boolean[][] defaultWorkDaySetup;
        private DefaultWorkDayHolder(){
            defaultShiftSetup = new HashMap<>();
            List<Job> jobs = WorkersUtils.getShiftWorkers();
            for (Job job: jobs) {
                int[] arr = new int[6];
                if(job != Job.Shift_Manager) {
                    arr[weekDayMorning] = workerDataController.getDefaultAmountRequired(1, "Morning", job.name());
                    arr[weekDayEvening] = workerDataController.getDefaultAmountRequired(1, "Evening", job.name());
                    arr[fridayMorning] = workerDataController.getDefaultAmountRequired(6, "Morning", job.name());
                    arr[fridayEvening] = workerDataController.getDefaultAmountRequired(6, "Evening", job.name());
                    arr[saturdayMorning] = workerDataController.getDefaultAmountRequired(7, "Morning", job.name());
                    arr[saturdayEvening] = workerDataController.getDefaultAmountRequired(7, "Evening", job.name());
                }else {
                    arr[weekDayMorning] = DEFAULT_SHIFT_MANAGER_AMOUNT;
                    arr[weekDayEvening] = DEFAULT_SHIFT_MANAGER_AMOUNT;
                    arr[fridayMorning] = DEFAULT_SHIFT_MANAGER_AMOUNT;
                    arr[fridayEvening] = DEFAULT_SHIFT_MANAGER_AMOUNT;
                    arr[saturdayMorning] = DEFAULT_SHIFT_MANAGER_AMOUNT;
                    arr[saturdayEvening] = DEFAULT_SHIFT_MANAGER_AMOUNT;
                }
                defaultShiftSetup.put(job, arr);
            }
            int numberOfDays = 7;
            int numberOfShifts = 2;
            defaultWorkDaySetup = new boolean[numberOfDays][numberOfShifts];
            for (int i = 0; i < numberOfDays; i++){
                boolean[] shifts = workerDataController.getDefaultWorkDayShifts(i + 1);
                if(shifts == null){
                    defaultWorkDaySetup[i][0] = false;
                    defaultWorkDaySetup[i][1] = false;
                }else{
                    defaultWorkDaySetup[i][0] = shifts[0];
                    defaultWorkDaySetup[i][1] = shifts[1];
                }
            }
        }

        private void setDefaultJobInShift(int dayOfTheWeek, ShiftType shiftType, Job job, int amount) throws InnerLogicException {
            if (job == Job.Shift_Manager){
                throw new InnerLogicException("Cannot change the default amount of shift manager role");
            }
            if (amount < 0)
                throw new InnerLogicException("cannot have a negative amount of workers");
            int shiftKind = getShiftKind(dayOfTheWeek, shiftType);
            int[] defaults = defaultShiftSetup.get(job);
            if (defaults == null){
                throw new InnerLogicException("There's no a default number workers for job: " + job);
            }
            defaults[shiftKind] = amount;
            workerDataController.setDefaultAmountRequired(dayOfTheWeek, shiftType.name(), job.name(), amount);
        }

        private int getDefaultJobInShift(Job role, int dayOfTheWeek, ShiftType shiftType) throws InnerLogicException {
            int shiftKind = getShiftKind(dayOfTheWeek, shiftType);
            int[] defaults = defaultShiftSetup.get(role);
            if (defaults == null){
                throw new InnerLogicException("There's no a default number workers for job: " + role);
            }
            return defaults[shiftKind];

        }

        private void setDefaultShiftInDay(int dayOfTheWeek, ShiftType shiftType, boolean changeTo) throws InnerLogicException {
            if (dayOfTheWeek < 1 || dayOfTheWeek > 7)
                throw new InnerLogicException("day must be between 1 to 7");
            int numShiftType = 0;
            if(shiftType == ShiftType.Evening) numShiftType = 1;
            defaultWorkDaySetup[dayOfTheWeek-1][numShiftType] = changeTo;
            workerDataController.setDefaultWorkDayShifts(dayOfTheWeek, shiftType.name(), changeTo);
        }

        private boolean getDefaultShiftInDay(int dayOfTheWeek, ShiftType shiftType) throws InnerLogicException {
            int numShiftType = 0;
            if(shiftType == ShiftType.Evening) numShiftType = 1;
            return defaultWorkDaySetup[dayOfTheWeek-1][numShiftType];
        }

        private int getShiftKind(int day, ShiftType shiftType) throws InnerLogicException {
            if (day < 1 || day > 7)
                throw new InnerLogicException("day must be between 1 to 7");
            if(day <= 5){
                if(shiftType == ShiftType.Morning) return weekDayMorning;
                else return weekDayEvening;
            }
            if(day == 6){
                if(shiftType == ShiftType.Morning) return fridayMorning;
                else return fridayEvening;
            }
            else{
                if(shiftType == ShiftType.Morning) return saturdayMorning;
                else return saturdayEvening;
            }
        }
    }
}
